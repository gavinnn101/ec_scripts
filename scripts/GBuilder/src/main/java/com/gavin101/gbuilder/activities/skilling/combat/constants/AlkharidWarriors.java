package com.gavin101.gbuilder.activities.skilling.combat.constants;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.map.RectArea;

public class AlkharidWarriors {
    public static final InventoryLoadout ALKHARID_WARRIOR_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.TROUT, 28)
            .setStrict(() -> !Inventory.contains(ItemID.TROUT))
            .setRefill(Calculations.random(80, 100));    // Buy between 80-100 trout if we run out;

    public static final int[] ALKHARID_WARRIOR_LOOT = {
            ItemID.BONES,
    };

    public static final RectArea ALKHARID_WARRIOR_AREA = new RectArea(3281, 3177, 3303, 3159);
}
