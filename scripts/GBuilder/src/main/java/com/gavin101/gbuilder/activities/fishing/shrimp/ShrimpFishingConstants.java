package com.gavin101.gbuilder.activities.fishing.shrimp;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;

public class ShrimpFishingConstants {
    public static final InventoryLoadout SHRIMP_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.SMALL_FISHING_NET)
            .setLoadoutStrict(Inventory::isFull);
}
