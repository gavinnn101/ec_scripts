package com.gavin101.accbuilder.constants.combat;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class ChickenCombat {
    public static InventoryLoadout CHICKEN_INVENTORY = new InventoryLoadout();
//            .addReq(ItemID.TROUT, 10)
//            .setEnabled(() -> !Inventory.contains(ItemID.TROUT))
//            .setLoadoutStrict(() -> !Inventory.contains(ItemID.TROUT));

    public static final RectArea CHICKEN_AREA = new RectArea(3172, 3302, 3183, 3289);

    public static final Map<Skill, LevelRange> CHICKEN_LEVEL_RANGES = Map.of(
            Skill.ATTACK,    new LevelRange(1, 10),
            Skill.STRENGTH,  new LevelRange(1, 10),
            Skill.DEFENCE,   new LevelRange(1, 10)
    );

    public static final String[] CHICKEN_LOOT = {
            "Feathers"
    };
}
