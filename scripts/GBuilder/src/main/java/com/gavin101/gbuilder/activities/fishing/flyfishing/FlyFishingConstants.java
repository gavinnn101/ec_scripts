package com.gavin101.gbuilder.activities.fishing.flyfishing;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.math.Calculations;

public class FlyFishingConstants {
    public static final InventoryLoadout FLY_FISHING_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.FLY_FISHING_ROD)
            .addReq(ItemID.FEATHER, Calculations.random(1000, 2001)) // Withdraw between 1000-2000 feathers
            .setEnabled(() -> !Inventory.contains(ItemID.FEATHER))
            .setRefill(Calculations.random(3000, 4001)) // Buy between 3000-4000 feathers if we don't have enough for our withdraw amount
            .setLoadoutStrict(Inventory::isFull);
}
