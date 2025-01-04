package com.gavin101.accbuilder.constants.combat;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class BarbarianCombat {
    public static InventoryLoadout BARBARIAN_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.TROUT, 20)
            .setEnabled(() -> !Inventory.contains(ItemID.TROUT))
            .setRefill(100)
            .setLoadoutStrict(() -> !Inventory.contains(ItemID.TROUT));

    public static final RectArea BARBARIAN_AREA = new RectArea(3073, 3426, 3087, 3413, 0);

    public static final Map<Skill, Integer> BARBARIAN_LEVEL_GOALS = Map.of(
            Skill.ATTACK, 30,
            Skill.STRENGTH, 30,
            Skill.DEFENCE, 30
    );

    public static final String[] BARBARIAN_LOOT = {

    };
}
