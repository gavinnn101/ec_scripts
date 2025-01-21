package com.gavin101.accbuilder.constants.mining;

import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.ArrayList;
import java.util.List;

public class Mining {
    public static final InventoryLoadout MINING_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.STEEL_PICKAXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.MINING) >= 6 && Skills.getRealLevel(Skill.MINING) < 21)
            .addReq(ItemID.MITHRIL_PICKAXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.MINING) >= 21 && Skills.getRealLevel(Skill.MINING) < 31)
            .addReq(ItemID.ADAMANT_PICKAXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.MINING) >= 31 && Skills.getRealLevel(Skill.MINING) < 41)
            .addReq(ItemID.RUNE_PICKAXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.MINING) >= 41)
            .setLoadoutStrict(Inventory::isFull);

    public static List<WorldTile> ROCK_TO_TILES_MAP(String rockName) {
        List<WorldTile> rockList = new ArrayList<>();
        switch (rockName) {
            case "Copper rocks":
                // Lumbridge swamp tiles
                rockList.add(new WorldTile(3230, 3148, 0)); // 2 rocks north-east (north side of rocks)
                rockList.add(new WorldTile(3228, 3145, 0)); // 2 rocks south-east (top side of rocks)
                rockList.add(new WorldTile(3230, 3146, 0)); // 2 rocks middle of north and south clusters
                // East Varrock mine tiles
                rockList.add(new WorldTile(3286, 3365, 0)); // 2 rocks middle (north side of rocks)
                rockList.add(new WorldTile(3289, 3362, 0)); // 2 rocks south-east
                rockList.add(new WorldTile(3286, 3361, 0)); // 2 rocks south
                // Rimmington mine tiles
                rockList.add(new WorldTile(2977, 3248, 0)); // 2 rocks top middle
                rockList.add(new WorldTile(2977, 3246, 0)); // 2 rocks bottom middle
                break;
            case "Tin rocks":
                // Lumbridge swamp tiles
                rockList.add(new WorldTile(3224, 3148, 0)); // 2 rocks north-west
                rockList.add(new WorldTile(3223, 3147, 0)); // 3 rocks west
                // East Varrock mine tiles
                rockList.add(new WorldTile(3282, 3363, 0)); // 2 rocks west
                break;
            case "Iron rocks":
                // East Varrock mine tiles
                rockList.add(new WorldTile(3286, 3368, 0)); // 2 rocks middle
                rockList.add(new WorldTile(3285, 3370, 0)); // 1 rock middle (north side of rock)
                rockList.add(new WorldTile(3288, 3371, 0)); // 1 rock east (north side of rock)
                // Rimmington mine tiles
                rockList.add(new WorldTile(2969, 3241, 0)); // 2 rocks west
                rockList.add(new WorldTile(2968, 3240, 0)); // 2 rocks south-west
                rockList.add(new WorldTile(2971, 3238, 0)); // 1 rock south-west
                rockList.add(new WorldTile(2981, 3234, 0)); // 2 rocks south-east (north side of rocks)
                break;
        }
        return rockList;
    }
}
