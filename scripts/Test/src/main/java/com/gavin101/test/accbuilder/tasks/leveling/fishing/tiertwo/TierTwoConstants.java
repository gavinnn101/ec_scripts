package com.gavin101.test.accbuilder.tasks.leveling.fishing.tiertwo;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;

public class TierTwoConstants {
    public static EquipmentLoadout FISHING_TIER_TWO_EQUIPMENT = new EquipmentLoadout();
    public static InventoryLoadout FISHING_TIER_TWO_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.FISHING_ROD)
            .addReq(ItemID.FISHING_BAIT, 500)
            .setEnabled(() -> !Inventory.contains(ItemID.FISHING_BAIT))
            .setRefill(1000)
            .setLoadoutStrict(Inventory::isFull);
    public static final RectArea LUMBRIDGE_FISHING_AREA = new RectArea(3245, 3159, 3239, 3149);
}
