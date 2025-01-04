package com.gavin101.accbuilder.constants.fishing;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;

public class ShrimpFishing {
    public static EquipmentLoadout SHRIMP_EQUIPMENT = new EquipmentLoadout();
    public static InventoryLoadout SHRIMP_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.SMALL_FISHING_NET)
            .setLoadoutStrict(Inventory::isFull);
    public static final RectArea LUMBRIDGE_FISHING_AREA = new RectArea(3245, 3159, 3239, 3149);
    public static final int SHRIMP_FISHING_LEVEL_GOAL = 5;

}
