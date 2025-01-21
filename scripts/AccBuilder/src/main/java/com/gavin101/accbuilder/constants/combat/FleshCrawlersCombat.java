package com.gavin101.accbuilder.constants.combat;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.PolyArea;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.List;
import java.util.Map;

public class FleshCrawlersCombat {
    public static InventoryLoadout FLESH_CRAWLERS_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.LOBSTER, Calculations.random(10, 21)) // Withdraw between 10-20 lobsters
            .setEnabled(() -> !Inventory.contains(ItemID.LOBSTER))
            .setRefill(Calculations.random(80, 101)) // Buy between 80-100 lobsters if we run out
            .setLoadoutStrict(() -> !Inventory.contains(ItemID.LOBSTER));

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

    public static final Map<Skill, LevelRange> FLESH_CRAWLERS_LEVEL_RANGES = Map.of(
            Skill.ATTACK,    new LevelRange(40, 50),
            Skill.STRENGTH,  new LevelRange(40, 50),
            Skill.DEFENCE,   new LevelRange(40, 50)
    );
}
