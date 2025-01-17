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

    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 440;
    }

    @Override
    public int onLoop() {
        Log.info("Going through gate into the rat pit.");
        int RAT_PIT_GATE_ID = 9719;
        GameObject gate = GameObjects.closest(RAT_PIT_GATE_ID);
        if (gate != null && gate.canReach()) {
            new EntityInteractEvent(gate, "Open").setEventCompleteCondition(
                    () -> PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 450, Calculations.random(1500, 3500)
            ).execute();
        }
        return ReactionGenerator.getNormal();
    }
}
