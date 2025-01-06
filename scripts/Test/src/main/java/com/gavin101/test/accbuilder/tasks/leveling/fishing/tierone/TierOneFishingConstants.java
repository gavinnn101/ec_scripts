package com.gavin101.test.accbuilder.tasks.leveling.fishing.tierone;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.RectArea;

import java.util.List;

public class TierOneFishingConstants {
    public static final RectArea LUMBRIDGE_FISHING_AREA = new RectArea(3245, 3159, 3239, 3149);
    public static final RectArea ALKHARID_FISHING_AREA = new RectArea(3264, 3152, 3280, 3136);

    public static final List<Area> FISHING_AREAS = List.of(
            LUMBRIDGE_FISHING_AREA,
            ALKHARID_FISHING_AREA
    );

    public static EquipmentLoadout FISHING_TIER_ONE_EQUIPMENT = new EquipmentLoadout();
    public static InventoryLoadout FISHING_TIER_ONE_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.SMALL_FISHING_NET)
            .setLoadoutStrict(Inventory::isFull);
}
