package com.gavin101.tutorialisland.leafs.QuesterRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;

public class ClimbDownLadderLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 250;
    }

    @Override
    public int onLoop() {
        GameObject ladder = GameObjects.closest("Ladder");
        if (ladder != null && ladder.canReach()) {
            new EntityInteractEvent(ladder, "Climb-down").setEventCompleteCondition(
                    () -> PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 260, Calculations.random(2500, 5000)
            ).execute();
        }
        return ReactionGenerator.getNormal();
    }
}
