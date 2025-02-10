package com.gavin101.gbuilder.breaks;

import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.events.random.RandomManager;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class BreakFinishedLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return FatigueTracker.isBreakFinished() && !RandomManager.isAutoLoginEnabled();
    }

    @Override
    public int onLoop() {
        Log.info("Break is over, enabling auto login.");
        ActivityManager.resumeActivityTimer();
        RandomManager.setAutoLoginEnabled(true);
        return ReactionGenerator.getNormal();
    }
}
