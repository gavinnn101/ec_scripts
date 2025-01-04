package com.gavin101.accbuilder.branches.fishing.flyfishing;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.fishing.FlyFishing;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

public class FlyFishingBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Players.localPlayer().isMoving() || Players.localPlayer().isAnimating()) {
            return false;
        }

        if (Skills.getRealLevel(Skill.FISHING) < FlyFishing.FLY_FISHING_LEVEL_GOAL) {
            Main.setActivity("Fly fishing", Skill.FISHING, FlyFishing.FLY_FISHING_LEVEL_GOAL);
            return true;
        }
        return false;
    }
}
