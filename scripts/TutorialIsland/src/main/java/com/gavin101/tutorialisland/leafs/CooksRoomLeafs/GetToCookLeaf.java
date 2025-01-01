package com.gavin101.tutorialisland.leafs.CooksRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.hintarrows.HintArrow;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.map.Tile;
import net.eternalclient.api.wrappers.walking.Walking;

public class GetToCookLeaf extends Leaf {
    @Override
    public boolean isValid() {
        NPC chef = NPCs.closest("Master Chef");
        return (chef != null && !chef.canReach())
        && PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 130;
    }

    @Override
    public int onLoop() {
        Log.info("Getting to cooks room.");
        GameObject door = GameObjects.closest(Constants.COOK_ENTRANCE_DOR_ID);
        if (door != null) {
            Log.debug("Opening cooks door.");
            new EntityInteractEvent(door, "Open").setEventCompleteCondition(
                    () -> PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 140, Calculations.random(2500, 5000)
            ).execute();
        } else {
            Log.debug("Walking to cooks entrance door.");
            Tile hintArrowTile = HintArrow.getWorldTile();
            if (!Players.localPlayer().getWorldTile().equals(hintArrowTile)) {
                if (hintArrowTile.canReach()) {
                    Walking.walk(hintArrowTile,
                            () -> GameObjects.closest(Constants.COOK_ENTRANCE_DOR_ID) != null
                    );
                }
            }
        }
        return ReactionGenerator.getNormal();
    }
}
