package com.gavin101.tutorialisland.leafs.MainlandLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.bank.BankLocation;
import net.eternalclient.api.events.LogoutEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.walking.Walking;

public class EndScriptLeaf extends Leaf {

    private final boolean walkToGE;

    public EndScriptLeaf(boolean walkToGE) {
        this.walkToGE = walkToGE;
    }
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 1000;
    }

    @Override
    public int onLoop() {
        Log.info("Finished with tutorial island, doing post tutorial tasks.");
        if (walkToGE && !Constants.GRAND_EXCHANGE_AREA.contains(Players.localPlayer())) {
            Log.info("Walking to the grand exchange.");
            Walking.walk(BankLocation.GRAND_EXCHANGE,
                    () -> Constants.GRAND_EXCHANGE_AREA.contains(Players.localPlayer())
            );
        } else {
            Log.info("Waiting 10-15 seconds before logging out to report account state to EF.");
            MethodProvider.sleep(10_000, 15_000);
            Log.info("logging out and ending the script");
            new LogoutEvent().setEventCompleteCondition(
                    () -> !Client.isLoggedIn(), Calculations.random(1500, 5000)
            ).execute();
            AbstractScript.setStopped();
        }
        return ReactionGenerator.getAFK();
    }
}
