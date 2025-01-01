package com.gavin101.breakhelper.leafs;

import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TakeBreakLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        MethodProvider.sleep(1000);
        return ReactionGenerator.getAFK();
    }
}
