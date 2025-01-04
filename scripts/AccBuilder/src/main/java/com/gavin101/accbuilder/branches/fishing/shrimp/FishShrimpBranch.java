package com.gavin101.accbuilder.branches.fishing.shrimp;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.fishing.ShrimpFishing;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

public class FishShrimpBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Players.localPlayer().isMoving() || Players.localPlayer().isAnimating()) {
            return false;
        }

        if (Skills.getRealLevel(Skill.FISHING) < ShrimpFishing.SHRIMP_FISHING_LEVEL_GOAL) {
            Main.setActivity("Small net fishing", Skill.FISHING, ShrimpFishing.SHRIMP_FISHING_LEVEL_GOAL);
            return true;
        }
        return false;
    }
}
