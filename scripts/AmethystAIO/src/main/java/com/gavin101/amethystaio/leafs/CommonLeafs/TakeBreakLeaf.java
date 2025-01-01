package com.gavin101.amethystaio.leafs.CommonLeafs;

import com.gavin101.amethystaio.BreakHelper;
import net.eternalclient.api.Client;
import net.eternalclient.api.events.LogoutEvent;
import net.eternalclient.api.events.random.RandomManager;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;

public class TakeBreakLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return BreakHelper.needToBreak();
    }

    @Override
    public int onLoop() {
        if (Client.isLoggedIn()) {
            Log.info("Logging out for a break");
            RandomManager.setAutoLoginEnabled(false);
            new LogoutEvent().setEventCompleteCondition(
                    () -> !Client.isLoggedIn(), Calculations.random(1250, 2500)
            ).execute();
        }

        long breakTime = BreakHelper.getBreakTimeLeftMs();
        Log.info("sleeping for: " +breakTime +" seconds while logged out for break");
        if (BreakHelper.isBreakFinished()) {
            Log.info("Break finished, logging back in");
            BreakHelper.setNeedToBreak(false);
            BreakHelper.resetSessionTimer();
        }
        return ReactionGenerator.getNormal();
    }
}