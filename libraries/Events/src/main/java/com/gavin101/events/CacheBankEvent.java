package com.gavin101.events;

import net.eternalclient.api.containers.bank.Bank;
import net.eternalclient.api.events.AbstractEvent;

public class CacheBankEvent extends AbstractEvent {

    private final boolean force;

    public CacheBankEvent(boolean force) {
        this.force = force;
    }

    @Override
    public int onLoop() {

        if (Bank.getCache().isCached()) {
            if (!force) {
                setCompleted("Bank is already cached.");
                return 0;
            }
        }

        if (Bank.traverseOpen(Bank.getClosestBank())) {
            if (Bank.isOpen()) {
                Bank.forceUpdateBankCache();
                if (Bank.close()) {
                    setCompleted("Bank successfully cached.");
                    return 0;
                }
            }
        }
        return 0;
    }
}