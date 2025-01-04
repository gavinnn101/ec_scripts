package com.gavin101.accbuilder.constants.combat;

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

    public static final Map<Skill, Integer> COW_LEVEL_GOALS = Map.of(
            Skill.ATTACK, 20,
            Skill.STRENGTH, 20,
            Skill.DEFENCE, 20
    );

    public static String[] COW_LOOT = {
            "Cowhide"
    };
}
