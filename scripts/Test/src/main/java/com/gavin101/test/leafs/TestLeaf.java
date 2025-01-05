package com.gavin101.test.leafs;

import com.gavin101.test.accbuilder.Goal;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

@RequiredArgsConstructor
public class TestLeaf extends Leaf {
    private final Goal goal;

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        Log.info("Our test leaf is being executed.");
        Log.info("Current goal: " +goal.getGoalDescription());
        return ReactionGenerator.getNormal();
    }
}
