package com.gavin101.accbuilder.constants.combat;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class BarbarianCombat {
    public static InventoryLoadout BARBARIAN_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.TROUT, Calculations.random(10, 21)) // Withdraw between 10-20 trout
            .setEnabled(() -> !Inventory.contains(ItemID.TROUT))
            .setRefill(Calculations.random(80, 101)) // Buy 80-100 trout if we don't have enough
            .setLoadoutStrict(() -> !Inventory.contains(ItemID.TROUT));

    public static final RectArea BARBARIAN_AREA = new RectArea(3073, 3426, 3087, 3413, 0);

    public static final Map<Skill, LevelRange> BARBARIAN_LEVEL_RANGES = Map.of(
            Skill.ATTACK,    new LevelRange(20, 30),
            Skill.STRENGTH,  new LevelRange(20, 30),
            Skill.DEFENCE,   new LevelRange(20, 30)
    );
}
