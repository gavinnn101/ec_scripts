package com.gavin101.gbuilder.activities.skilling.combat.constants;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.map.RectArea;

public class Barbarians {
    public static final InventoryLoadout BARBARIAN_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.TROUT, 28)
            .setStrict(() -> !Inventory.contains(ItemID.TROUT))
            .setRefill(Calculations.random(80, 100)); // Buy 80-100 trout if we don't have enough

    public static final int[] BARBARIAN_LOOT = {
            ItemID.BONES,
    };

    public static final RectArea BARBARIAN_AREA = new RectArea(3073, 3426, 3087, 3413, 0);
}
