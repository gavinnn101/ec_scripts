package com.gavin101.gbuilder.activities.skilling.woodcutting.common.constants;

import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.PolyArea;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final int TREE_CHOPPING_ANIMATION = 877;

    public static InventoryLoadout WOODCUTTING_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.IRON_AXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.WOODCUTTING) >= 1 && Skills.getRealLevel(Skill.WOODCUTTING) < 6)
            .setPrice(Calculations.random(1000, 1500))
            .addReq(ItemID.STEEL_AXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.WOODCUTTING) >= 6 && Skills.getRealLevel(Skill.WOODCUTTING) < 21)
            .setPrice(Calculations.random(1000, 1500))
            .addReq(ItemID.MITHRIL_AXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.WOODCUTTING) >= 21 && Skills.getRealLevel(Skill.WOODCUTTING) < 31)
            .setPrice(Calculations.random(1000, 1500))
            .addReq(ItemID.ADAMANT_AXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.WOODCUTTING) >= 31 && Skills.getRealLevel(Skill.WOODCUTTING) < 41)
            .setPrice(Calculations.random(1000, 1500))
            .addReq(ItemID.RUNE_AXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.WOODCUTTING) >= 41)
            .setLoadoutStrict(Inventory::isFull);

    public static List<Area> TREE_TO_AREAS_MAP(String treeName) {
        List<Area> treeAreaList = new ArrayList<>();
        switch (treeName) {
            case "Tree":
//                treeAreaList.add(new RectArea(3174, 3255, 3197, 3208)); // Behind Lumbridge castle (excluded because low level dies to mugger)
                treeAreaList.add(new RectArea(3158, 3417, 3172, 3375)); // East of Gertrude's house
                treeAreaList.add(new RectArea(3117, 3439, 3145, 3420)); // South of cooking guild
                break;
            case "Oak tree":
                treeAreaList.add(new RectArea(3171, 3422, 3158, 3406)); // East of Gertrude's house (smaller for just oaks)
//                treeAreaList.add(new RectArea(3098, 3248, 3103, 3239)); // East of Draynor bank (excluded because low level dies to mugger)
                treeAreaList.add(new RectArea(2946, 3410, 2956, 3395)); // North-West of Falador walls
                break;
            case "Willow tree":
                treeAreaList.add(new PolyArea(
                        new WorldTile(3081, 3238, 0),
                        new WorldTile(3086, 3240, 0),
                        new WorldTile(3094, 3228, 0),
                        new WorldTile(3087, 3224, 0)
                ));  // Draynor village
                break;
            case "Maple tree":
                break;
            case "Yew tree":
                break;
            case "Magic tree":
                break;
        }
        return treeAreaList;
    }

    public static final int[] WOODCUTTING_LOOT = {
            ItemID.BIRD_NEST,
            ItemID.BIRD_NEST_5071,
            ItemID.BIRD_NEST_5072,
            ItemID.BIRD_NEST_5073,
            ItemID.BIRD_NEST_5074,
            ItemID.BIRD_NEST_5075,
            ItemID.BIRD_NEST_7413,
            ItemID.BIRD_NEST_13653,
            ItemID.BIRD_NEST_22798,
            ItemID.BIRD_NEST_22800
    };
}
