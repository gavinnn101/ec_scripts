package com.gavin101.gbuilder.activities.skilling.woodcutting.chopnormaltrees;

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

public class ChopNormalTrees {
    private static final String activityName = "Chop normal trees";

    public static final Branch ACTIVITY_BRANCH = new ValidateActivityBranch(
            ActivityManager.getActivity(activityName)).addLeafs(
            GetCurrentActivityLoadoutLeaf.builder()
                    .buyRemainder(true)
                    .build(),
            new IsAfkBranch().addLeafs(
                    new LootItemsLeaf(Constants.WOODCUTTING_LOOT, 10),
                    new GoToAreaLeaf(GLib.getRandomArea(Constants.TREE_TO_AREAS_MAP("Tree"))),
                    new ChopTreeLeaf("Tree")
            )
    );

    public static final SkillActivity ACTIVITY = SkillActivity.builder()
            .name(activityName)
            .branch(ACTIVITY_BRANCH)
            .activitySkill(Skill.FISHING)
            .inventoryLoadout(Constants.WOODCUTTING_INVENTORY)
            .minLevel(1)
            .maxLevel(99)
            .build();
}
