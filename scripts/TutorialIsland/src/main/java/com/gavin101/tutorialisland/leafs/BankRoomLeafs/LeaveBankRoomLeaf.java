package com.gavin101.tutorialisland.leafs.BankRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;

public class LeaveBankRoomLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 540;
    }

    @Override
    public int onLoop() {
        Log.info("Leaving bank room");
        GameObject door = GameObjects.closest(Constants.ACCOUNT_GUIDE_EXIT_DOOR_ID);
        if (door != null) {
            Log.debug("Interacting with door 'Open'");
            new EntityInteractEvent(door, "Open").setEventCompleteCondition(
                    () -> PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 550, Calculations.random(2000, 5000)
            ).execute();
        }
        return ReactionGenerator.getNormal();
    }
}
