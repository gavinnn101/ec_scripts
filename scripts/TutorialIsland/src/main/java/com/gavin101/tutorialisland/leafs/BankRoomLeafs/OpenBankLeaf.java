package com.gavin101.tutorialisland.leafs.BankRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.containers.bank.Bank;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;

public class OpenBankLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 510;
    }

    @Override
    public int onLoop() {
        Log.info("Opening bank booth.");
        GameObject bankBooth = GameObjects.closest("Bank booth");
        if (bankBooth != null && bankBooth.canReach()) {
            Log.debug("Interacting with bank booth 'Use'.");
            new EntityInteractEvent(bankBooth, "Use").setEventCompleteCondition(
                    Bank::isOpen, Calculations.random(3000, 5000)
            ).execute();
        }
        return ReactionGenerator.getNormal();
    }
}
