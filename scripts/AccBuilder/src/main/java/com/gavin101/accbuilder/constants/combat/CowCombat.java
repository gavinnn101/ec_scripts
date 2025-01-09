package com.gavin101.accbuilder.constants.combat;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class CowCombat {
    public static InventoryLoadout COW_INVENTORY = new InventoryLoadout();
//            .addReq(ItemID.TROUT, 10)
//            .setEnabled(() -> !Inventory.contains(ItemID.TROUT))
//            .setLoadoutStrict(() -> !Inventory.contains(ItemID.TROUT));

    public static final RectArea COW_AREA = new RectArea(3194, 3300, 3209, 3285);

    public static final Map<Skill, LevelRange> COW_LEVEL_RANGES = Map.of(
            Skill.ATTACK,    new LevelRange(10, 20),
            Skill.STRENGTH,  new LevelRange(10, 20),
            Skill.DEFENCE,   new LevelRange(10, 20)
    );

    public static String[] COW_LOOT = {
            "Cowhide"
    };
}
