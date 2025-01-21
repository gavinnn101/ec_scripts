package com.gavin101.GLib.leafs.common;

import net.eternalclient.api.events.ToggleRunEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.walking.Walking;

public class EnableRunningLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int energyThreshold = Calculations.random(15, 100);
        return Walking.getRunEnergy() >= energyThreshold && !Walking.isRunEnabled();
    }

    @Override
    public int onLoop() {
        Log.info("Enabling running");
        new ToggleRunEvent(true).setEventCompleteCondition(
                Walking::isRunEnabled, Calculations.random(250, 350)
        ).execute();
        return ReactionGenerator.getPredictable();
    }
}
