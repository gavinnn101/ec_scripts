package com.gavin101.tutorialisland.leafs.BankRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.containers.bank.Bank;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;

public class CloseBankLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 520 && Bank.isOpen();
    }

    @Override
    public int onLoop() {
        if (Bank.isOpen()) {
            Log.info("Closing bank.");
            if (Bank.close()) {
                MethodProvider.sleepUntil(() -> !Bank.isOpen(), Calculations.random(1500, 3000));
            }
        }
        return ReactionGenerator.getNormal();
    }
}
