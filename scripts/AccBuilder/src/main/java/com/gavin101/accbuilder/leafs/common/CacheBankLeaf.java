package com.gavin101.accbuilder.leafs.common;

import com.gavin101.events.CacheBankEvent;
import net.eternalclient.api.containers.bank.Bank;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;

public class CacheBankLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !Bank.getCache().isCached();
    }

    @Override
    public int onLoop() {
        new CacheBankEvent(false).execute();
        return ReactionGenerator.getNormal();
    }
}
