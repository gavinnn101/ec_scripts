package com.gavin101.accbuilder.leafs.common;

import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.events.loadout.WithdrawLoadoutEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class GetLoadoutLeaf extends Leaf {
    private final EquipmentLoadout equipmentLoadout;
    private final InventoryLoadout inventoryLoadout;

    public GetLoadoutLeaf(EquipmentLoadout equipmentLoadout, InventoryLoadout inventoryLoadout) {
        this.equipmentLoadout = equipmentLoadout;
        this.inventoryLoadout = inventoryLoadout;
    }

    @Override
    public boolean isValid() {
        return !loadoutsFulfilled();
    }

    @Override
    public int onLoop() {
        Log.info("Getting loadout");
        new WithdrawLoadoutEvent(inventoryLoadout)
                .addEquipmentLoadout(equipmentLoadout)
                .setBuyRemainder(true)
                .setEventCompleteCondition(this::loadoutsFulfilled)
                .execute();
        return ReactionGenerator.getNormal();
    }

    public boolean loadoutsFulfilled() {
        return equipmentLoadout.isFulfilled() && inventoryLoadout.isFulfilled();
    }
}
