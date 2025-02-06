package com.gavin101.gbuilder.breaks;

import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.Client;
import net.eternalclient.api.events.LogoutEvent;
import net.eternalclient.api.events.random.RandomManager;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TakeBreakLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return FatigueTracker.shouldTakeBreak() && RandomManager.isAutoLoginEnabled();
    }

    @Override
    public int onLoop() {
        Log.info("Logging out for break");
        FatigueTracker.startBreak();
        RandomManager.setAutoLoginEnabled(false);
        new LogoutEvent().setEventCompleteCondition(() -> !Client.isLoggedIn()).execute();
        return ReactionGenerator.getAFK();
    }
}
