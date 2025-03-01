package com.gavin101.gbuilder.activities.skilling.combat.constants;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.PolyArea;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;

import java.util.List;

public class FleshCrawlers {
    public static final InventoryLoadout FLESH_CRAWLERS_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.LOBSTER, Calculations.random(20, 28)) // Withdraw between 20-28 lobsters
            .setStrict(() -> !Inventory.contains(ItemID.LOBSTER))
            .setRefill(Calculations.random(100, 201)); // Buy between 100-200 lobsters if we run out

    public static final List<Area> FLESH_CRAWLERS_AREAS = List.of(
            new RectArea(1987, 5244, 1996, 5232),   // North-east room
            new RectArea(2000, 5208, 2011, 5197),   // south-east room
            new PolyArea(
                    new WorldTile(2034, 5195, 0),
                    new WorldTile(2037, 5184, 0),
                    new WorldTile(2047, 5185, 0),
                    new WorldTile(2047, 5195, 0)
            )                                                       // South-west room
    );
}
