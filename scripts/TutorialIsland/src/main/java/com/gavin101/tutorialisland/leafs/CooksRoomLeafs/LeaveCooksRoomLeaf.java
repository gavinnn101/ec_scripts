package com.gavin101.tutorialisland.leafs.CooksRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.hintarrows.HintArrow;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.map.Tile;
import net.eternalclient.api.wrappers.walking.Walking;

public class LeaveCooksRoomLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 170;
    }

    @Override
    public int onLoop() {
        Log.info("Leaving cooks room.");
        GameObject door = GameObjects.closest(Constants.COOK_EXIT_DOOR_ID);
        if (door != null) {
            Log.debug("Opening cooks exit door.");
            new EntityInteractEvent(door, "Open").setEventCompleteCondition(
                    () -> PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 200, Calculations.random(2500, 5000)
            ).execute();
        } else {
            Log.debug("Walking to cooks exit door.");
            Tile hintArrowTile = HintArrow.getWorldTile();
            if (!Players.localPlayer().getWorldTile().equals(hintArrowTile)) {
                if (hintArrowTile.canReach()) {
                    Walking.walk(hintArrowTile,
                            () -> Players.localPlayer().getWorldTile().equals(hintArrowTile)
                    );
                }
            }
        }
        return ReactionGenerator.getPredictable();
    }
}
