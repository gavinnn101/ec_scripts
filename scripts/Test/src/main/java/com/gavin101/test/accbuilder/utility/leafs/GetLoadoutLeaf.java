package com.gavin101.test.accbuilder.utility.leafs;

import lombok.Builder;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.events.loadout.WithdrawLoadoutEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

import static com.gavin101.GLib.GLib.loadoutsFulfilled;

@Builder
public class GetLoadoutLeaf extends Leaf {
    private final EquipmentLoadout equipmentLoadout;
    private final InventoryLoadout inventoryLoadout;

    @Builder.Default
    private final boolean buyRemainder = false;

    @Override
    public boolean isValid() {
        return !loadoutsFulfilled(equipmentLoadout, inventoryLoadout);
    }

    @Override
    public int onLoop() {
        Log.info("Getting loadout");
        new WithdrawLoadoutEvent(inventoryLoadout)
                .addEquipmentLoadout(equipmentLoadout)
                .setBuyRemainder(buyRemainder)
                .setEventCompleteCondition(() -> loadoutsFulfilled(equipmentLoadout, inventoryLoadout))
                .execute();
        return ReactionGenerator.getNormal();
    }
}

