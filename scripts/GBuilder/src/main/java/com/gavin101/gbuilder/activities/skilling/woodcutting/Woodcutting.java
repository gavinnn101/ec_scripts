package com.gavin101.gbuilder.activities.skilling.woodcutting;

import com.gavin101.GLib.GLib;
import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.GLib.leafs.common.GoToAreaLeaf;
import com.gavin101.gbuilder.activities.skilling.woodcutting.common.constants.Constants;
import com.gavin101.gbuilder.activities.skilling.woodcutting.common.leafs.ChopTreeLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.SkillActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import com.gavin101.gbuilder.utility.leafs.LootItemsLeaf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;


public class Woodcutting {
    @Getter
    @RequiredArgsConstructor
    public enum TreeType {
        TREE("Tree", 1, 15),
        OAK("Oak tree", 15, 31),
        WILLOW("Willow tree", 31, 99);

        private final String name;
        private final int minLevel;
        private final int maxLevel;
    }

    public static SkillActivity createActivity(TreeType treeType) {
        String treeName = treeType.getName();

        String activityName = String.format("Chopping %ss", treeName);

        return SkillActivity.builder()
                .name(activityName)
                .branchSupplier(() -> createBranch(treeName, activityName))
                .activitySkill(Skill.WOODCUTTING)
                .inventoryLoadout(Constants.WOODCUTTING_INVENTORY)
                .minLevel(treeType.getMinLevel())
                .maxLevel(treeType.getMaxLevel())
                .validator(() -> {
                    if (treeType.equals(TreeType.WILLOW)) {
                        int wizardLevel = 7;
                        return !GLib.isNpcAggressive(wizardLevel);
                    }
                    return true;
                })
                .build();
    }

    private static Branch createBranch(String treeName, String activityName) {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(true)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new LootItemsLeaf(Constants.WOODCUTTING_LOOT, 10),
                        new GoToAreaLeaf(GLib.getRandomArea(Constants.TREE_TO_AREAS_MAP(treeName))),
                        new ChopTreeLeaf(treeName)
                )
        );
    }
}
