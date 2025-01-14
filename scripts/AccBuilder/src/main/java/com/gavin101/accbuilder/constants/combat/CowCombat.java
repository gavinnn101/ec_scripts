package com.gavin101.accbuilder.constants.combat;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.PolyArea;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.List;
import java.util.Map;

public class CowCombat {
    public static InventoryLoadout COW_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.HERRING, 4)
            .setEnabled(() -> !Inventory.contains(ItemID.HERRING))
            .setLoadoutStrict(() -> !Inventory.contains(ItemID.HERRING) || Inventory.isFull());

    public static final Map<Skill, LevelRange> COW_LEVEL_RANGES = Map.of(
            Skill.ATTACK,    new LevelRange(10, 20),
            Skill.STRENGTH,  new LevelRange(10, 20),
            Skill.DEFENCE,   new LevelRange(10, 20)
    );

    public static int[] COW_LOOT = {
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
