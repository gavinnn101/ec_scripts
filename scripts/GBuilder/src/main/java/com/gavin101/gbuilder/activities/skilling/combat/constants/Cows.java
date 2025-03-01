package com.gavin101.gbuilder.activities.skilling.combat.constants;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.PolyArea;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;

import java.util.List;

public class Cows {
    public static final InventoryLoadout COW_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.HERRING, 4)
            .setStrict(() -> !Inventory.contains(ItemID.HERRING))
            .setLoadoutStrict(Inventory::isFull);

    public static final int[] COW_LOOT = {
            ItemID.BONES,
            ItemID.COWHIDE
    };

    public static final List<Area> COW_COMBAT_AREAS = List.of(
            new PolyArea(
                    new WorldTile(3154, 3345, 0),
                    new WorldTile(3199, 3333, 0),
                    new WorldTile(3211, 3309, 0),
                    new WorldTile(3154, 3319, 0)
            ),                                                          // Bigger north of Lumbridge
            new RectArea(3194, 3300, 3209, 3285),        // Smaller north of Lumbridge
            new PolyArea(
                    new WorldTile(3240, 3298, 0),
                    new WorldTile(3263, 3298, 0),
                    new WorldTile(3265, 3255, 0),
                    new WorldTile(3254, 3255, 0)
            ),                                                           // East of Lumbridge
            new RectArea(3021, 3313, 3042, 3298, 0) // South of Falador

    );
}
