package com.gavin101.gbuilder.activities.skilling.mining;

import com.gavin101.GLib.GLib;
import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.GLib.leafs.common.GoToAreaLeaf;
import com.gavin101.gbuilder.activities.skilling.mining.constants.MiningConstants;
import com.gavin101.gbuilder.activities.skilling.mining.leafs.MineRockLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.SkillActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class Mining {
    @Getter
    @RequiredArgsConstructor
    public enum RockType {
        COPPER("Copper rocks", 1, 15),
        TIN("Tin rocks", 1, 15),
        IRON("Iron rocks", 15, 55);

        private final String name;
        private final int minLevel;
        private final int maxLevel;
    }

    public static SkillActivity createActivity(RockType rockType) {
        String rockName = rockType.getName();

        String activityName = String.format("Mining: %s", rockName);

        int maxLevel;
        if (rockType.equals(RockType.IRON)) {
            maxLevel = rockType.getMaxLevel() + Calculations.random(-5, 7);
        } else {
            maxLevel = rockType.getMaxLevel();
        }

        return SkillActivity.builder()
                .name(activityName)
                .branchSupplier(() -> createBranch(rockName, activityName))
                .activitySkill(Skill.MINING)
                .inventoryLoadout(MiningConstants.MINING_INVENTORY)
                .minLevel(rockType.getMinLevel())
                .maxLevel(maxLevel)
                .validator(Quest.DORICS_QUEST::isFinished)
                .build();
    }

    private static Branch createBranch(String rockName, String activityName) {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(true)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new GoToAreaLeaf(GLib.getRandomArea(MiningConstants.ROCK_TO_AREAS_MAP(rockName))),
                        new MineRockLeaf(rockName)
                )
        );
    }
}
