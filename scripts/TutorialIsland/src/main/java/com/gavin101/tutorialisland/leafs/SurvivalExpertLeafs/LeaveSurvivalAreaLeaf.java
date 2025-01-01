package com.gavin101.tutorialisland.leafs.SurvivalExpertLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;

public class LeaveSurvivalAreaLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 120;
    }

    @Override
    public int onLoop() {
        Log.info("Leaving the survival area.");
        GameObject gate = GameObjects.closest("Gate");
        if (gate != null) {
            Log.debug("Clicking 'open' on gate.");
            new EntityInteractEvent(gate, "Open").setEventCompleteCondition(
                    () -> PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 130, Calculations.random(500, 1500)
            ).execute();
        }
        return ReactionGenerator.getAFK();
    }
}
