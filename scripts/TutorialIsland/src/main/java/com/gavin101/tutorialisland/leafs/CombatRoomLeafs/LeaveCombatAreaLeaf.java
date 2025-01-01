package com.gavin101.tutorialisland.leafs.CombatRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.walking.Walking;

public class LeaveCombatAreaLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 500;
    }

    @Override
    public int onLoop() {
        Log.info("Leaving combat area.");
        GameObject ladder = GameObjects.closest("Ladder");
        if (ladder != null && ladder.canReach()) {
            Log.debug("Interacting with ladder 'Climb-up'.");
            new EntityInteractEvent(ladder, "Climb-up").setEventCompleteCondition(
                    () -> PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 510, Calculations.random(1500, 3500)
            ).execute();
        } else if (!Constants.COMBAT_AREA_BOTTOM_LADDER.contains(Players.localPlayer())) {
            Log.debug("Walking to ladder area.");
            Walking.walk(Constants.COMBAT_AREA_BOTTOM_LADDER.getRandomTile(),
                    () -> Constants.COMBAT_AREA_BOTTOM_LADDER.contains(Players.localPlayer())
            );
        }
        return ReactionGenerator.getNormal();
    }
}
