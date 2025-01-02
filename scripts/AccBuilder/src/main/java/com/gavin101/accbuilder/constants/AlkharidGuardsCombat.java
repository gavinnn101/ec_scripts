package com.gavin101.accbuilder.constants;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class AlkharidGuardsCombat {
    public static InventoryLoadout ALKHARID_GUARD_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.TROUT, 10)
            .setEnabled(() -> !Inventory.contains(ItemID.TROUT))
            .setRefill(100)
            .setLoadoutStrict(() -> !Inventory.contains(ItemID.TROUT));

    public static final RectArea ALKHARID_GUARD_AREA = new RectArea(3281, 3177, 3303, 3159);

    public static final Map<Skill, Integer> ALKHARID_GUARD_LEVEL_GOALS = Map.of(
            Skill.ATTACK, 30,
            Skill.STRENGTH, 30,
            Skill.DEFENCE, 30
    );

    public static final String[] ALKHARID_GUARD_LOOT = {

    };
}
