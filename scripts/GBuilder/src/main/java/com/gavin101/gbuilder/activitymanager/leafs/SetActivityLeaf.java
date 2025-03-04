package com.gavin101.gbuilder.activitymanager.leafs;

import com.gavin101.gbuilder.activitymanager.ActivityManager;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;

public class SetActivityLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !ActivityManager.currentActivityIsValid();
    }

    @Override
    public int onLoop() {
        ActivityManager.setCurrentActivity();
        return ReactionGenerator.getPredictable();
    }
}
