package com.gavin101.test.leafs;

import com.gavin101.test.Main;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TestLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        Log.info("Our test leaf is being executed.");
        Log.info("Goals: " +Main.accBuilder.getGoals());
        Log.info("Current goal: " +Main.accBuilder.getCurrentGoal());
        return ReactionGenerator.getNormal();
    }
}
