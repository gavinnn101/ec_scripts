package com.gavin101.accbuilder.branches.fishing.bait;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

public class BaitFishingBranch extends Branch {
    int BAIT_FISHING_LEVEL_GOAL = 20;
    @Override
    public boolean isValid() {
        if (Players.localPlayer().isMoving()) {
            return false;
        }

        if (Skills.getRealLevel(Skill.FISHING) < BAIT_FISHING_LEVEL_GOAL) {
            Main.currentSkillToTrain = Skill.FISHING;
            Main.currentLevelGoal = BAIT_FISHING_LEVEL_GOAL;
            return true;
        }
        return false;
    }
}
