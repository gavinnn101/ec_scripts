package com.gavin101.accbuilder.constants.fishing;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class BaitFishing {
    public static EquipmentLoadout BAIT_FISHING_EQUIPMENT = new EquipmentLoadout();

    public static InventoryLoadout BAIT_FISHING_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.FISHING_ROD)
            .addReq(ItemID.FISHING_BAIT, Calculations.random(500, 751)) // Withdraw between 500-751 fishing bait
            .setEnabled(() -> !Inventory.contains(ItemID.FISHING_BAIT))
            .setRefill(Calculations.random(1000, 2001)) // Buy between 1000-2000 fishing bait if we don't have enough for our withdraw amount
            .setLoadoutStrict(Inventory::isFull);

    public static final Map<Skill, LevelRange> BAIT_FISHING_LEVEL_RANGES = Map.of(
            Skill.FISHING,    new LevelRange(5, 20)
    );
}
