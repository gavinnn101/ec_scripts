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
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

public class Woodcutting {
    public static SkillActivity createActivity(String treeName, int minLevel, int maxLevel) {
        String activityName = String.format("Chopping %ss", treeName);

        return SkillActivity.builder()
                .name(activityName)
                .branchSupplier(() -> createBranch(treeName, activityName))
                .activitySkill(Skill.WOODCUTTING)
                .inventoryLoadout(Constants.WOODCUTTING_INVENTORY)
                .minLevel(minLevel)
                .maxLevel(maxLevel)
                .validator(() -> {
                    if (treeName.equals("Willow tree")) {
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
