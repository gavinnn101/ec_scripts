package com.gavin101.gbuilder.breaks;

import net.eternalclient.api.Client;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;

public class LoggedOutLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !Client.isLoggedIn();
    }

    @Override
    public int onLoop() {
        return ReactionGenerator.getPredictable();
    }
}
