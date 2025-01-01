package com.gavin101.accbuilder.branches.fishing.shrimp;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

public class FishShrimpBranch extends Branch {
    int SHRIMP_FISHING_LEVEL_GOAL = 5;
    @Override
    public boolean isValid() {
        if (Players.localPlayer().isMoving()) {
            return false;
        }

        if (Skills.getRealLevel(Skill.FISHING) < SHRIMP_FISHING_LEVEL_GOAL) {
            Main.currentSkillToTrain = Skill.FISHING;
            Main.currentLevelGoal = SHRIMP_FISHING_LEVEL_GOAL;
            return true;
        }
        return false;
    }
}
