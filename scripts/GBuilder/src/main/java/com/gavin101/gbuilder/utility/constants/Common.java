package com.gavin101.gbuilder.utility.constants;


import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;

import java.util.Map;

public class Common {
    public static final EquipmentLoadout EMPTY_EQUIPMENT_LOADOUT = new EquipmentLoadout();
    public static final InventoryLoadout EMPTY_INVENTORY_LOADOUT = new InventoryLoadout();
    public static final int[] EMPTY_LOOT = {};

    public static final Map<Integer, Integer> itemsToSell = Map.of(
//            ItemID.COWHIDE, 9999,
            ItemID.LEATHER, 9999,
            ItemID.IRON_ORE, 9999
    );
}

