package com.gavin101.gbuilder.fatiguetracker;

import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;

public class ManageFatigueLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return FatigueTracker.shouldIncreaseFatigue();
    }

    @Override
    public int onLoop() {
        FatigueTracker.increaseFatigue();
        return ReactionGenerator.getLowPredictable();
    }
}
