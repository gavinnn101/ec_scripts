package com.gavin101.test.accbuilder.tasks.leveling.fishing.tierthree;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;

public class TierThreeConstants {
    public static EquipmentLoadout FLY_FISHING_EQUIPMENT = new EquipmentLoadout();
    public static InventoryLoadout FLY_FISHING_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.FLY_FISHING_ROD)
            .addReq(ItemID.FEATHER, 2000)
            .setEnabled(() -> !Inventory.contains(ItemID.FEATHER))
            .setRefill(4000)
            .setLoadoutStrict(Inventory::isFull);
    public static final RectArea BARB_FLY_FISHING_AREA = new RectArea(3099, 3439, 3109, 3423);
}
