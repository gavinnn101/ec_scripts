package com.gavin101.accbuilder.constants;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;

public class BaitFishing {
    public static EquipmentLoadout BAIT_FISHING_EQUIPMENT = new EquipmentLoadout();
    public static InventoryLoadout BAIT_FISHING_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.FISHING_ROD)
            .addReq(ItemID.FISHING_BAIT, 500)
            .setEnabled(() -> !Inventory.contains(ItemID.FISHING_BAIT))
            .setRefill(1000)
            .setLoadoutStrict(Inventory::isFull);
    public static final RectArea LUMBRIDGE_FISHING_AREA = new RectArea(3245, 3159, 3239, 3149);
}
