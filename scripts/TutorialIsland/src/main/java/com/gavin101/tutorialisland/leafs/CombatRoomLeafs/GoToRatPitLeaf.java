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
import net.eternalclient.api.wrappers.hintarrows.HintArrow;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.map.Tile;
import net.eternalclient.api.wrappers.walking.Walking;

import java.util.Arrays;

public class GoToRatPitLeaf extends Leaf {

    private static final int[] GATE_IDS = {Constants.RAT_PIT_LEFT_GATE_ID, Constants.RAT_PIT_RIGHT_GATE_ID};

    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 440;
    }

    @Override
    public int onLoop() {
        Log.info("Trying to get into the rat pits.");
        if (!enterGate()) {
            Log.debug("Going into the rat pit.");
            Tile hintArrowTile = HintArrow.getWorldTile();
            if (!Players.localPlayer().getWorldTile().equals(hintArrowTile)) {
                Walking.walk(hintArrowTile, () -> Players.localPlayer().getWorldTile().equals(hintArrowTile));
            }
        }
        return ReactionGenerator.getNormal();
    }

    private boolean enterGate() {
        GameObject gate = GameObjects.closest(obj -> Arrays.stream(GATE_IDS).anyMatch(id -> obj.getID() == id));
        if (gate != null) {
            Log.info("Attempting to open gate: " + gate.getID());
            new EntityInteractEvent(gate, "Open").setEventCompleteCondition(
                    () -> PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 450, Calculations.random(1500, 3500)
            ).execute();
            return true;
        }
        Log.warn("No gate found.");
        return false;
    }
}
