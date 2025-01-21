package com.gavin101.accbuilder.constants.combat;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class AlkharidGuardsCombat {
    public static final InventoryLoadout ALKHARID_GUARD_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.TROUT, Calculations.random(10, 21)) // Withdraw between 10-20 trout
            .setEnabled(() -> !Inventory.contains(ItemID.TROUT))
            .setRefill(Calculations.random(80, 101)) // Buy between 80-100 trout if we run out
            .setLoadoutStrict(() -> !Inventory.contains(ItemID.TROUT));

    public static final RectArea ALKHARID_GUARD_AREA = new RectArea(3281, 3177, 3303, 3159);

    public static final Map<Skill, LevelRange> ALKHARID_GUARDS_LEVEL_RANGES = Map.of(
            Skill.ATTACK,    new LevelRange(30, 40),
            Skill.STRENGTH,  new LevelRange(30, 40),
            Skill.DEFENCE,   new LevelRange(30, 40)
    );
}
