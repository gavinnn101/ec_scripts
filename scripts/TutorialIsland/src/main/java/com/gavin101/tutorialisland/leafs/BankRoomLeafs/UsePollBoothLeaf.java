package com.gavin101.tutorialisland.leafs.BankRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.Dialogues;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.containers.bank.Bank;
import net.eternalclient.api.events.DialogueEvent;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;

public class UsePollBoothLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 520;
    }

    @Override
    public int onLoop() {
        Log.info("Using poll booth.");
        if (Bank.isOpen()) {
            Log.debug("Closing bank.");
            if (Bank.close()) {
                MethodProvider.sleepUntil(() -> !Bank.isOpen(), Calculations.random(1500, 3000));
            }
        } else if (Dialogues.inDialogue()) {
            new DialogueEvent().setEventCompleteCondition(
                    () -> PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 525, Calculations.random(1500, 5000)
            ).execute();
        } else {
            GameObject pollBooth = GameObjects.closest("Poll booth");
            if (pollBooth != null && pollBooth.canReach()) {
                Log.debug("Interacting with poll booth 'Use'.");
                new EntityInteractEvent(pollBooth, "Use").setEventCompleteCondition(
                        Dialogues::inDialogue, Calculations.random(1500, 5000)
                ).execute();
            }
        }
        return ReactionGenerator.getPredictable();
    }
}
