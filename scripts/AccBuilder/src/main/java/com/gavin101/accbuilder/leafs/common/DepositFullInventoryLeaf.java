package com.gavin101.accbuilder.leafs.common;

import com.gavin101.accbuilder.constants.Common;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.loadout.WithdrawLoadoutEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class DepositFullInventoryLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Inventory.isFull();
    }

    @Override
    public int onLoop() {
        Log.info("Depositing full inventory.");
        new WithdrawLoadoutEvent(Common.EMPTY_INVENTORY_LOADOUT.setLoadoutStrict()).execute();
        return ReactionGenerator.getNormal();
    }
}
