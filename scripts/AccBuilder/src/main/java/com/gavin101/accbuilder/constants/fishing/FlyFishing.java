package com.gavin101.accbuilder.constants.fishing;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class FlyFishing {
    public static EquipmentLoadout FLY_FISHING_EQUIPMENT = new EquipmentLoadout();

    public static InventoryLoadout FLY_FISHING_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.FLY_FISHING_ROD)
            .addReq(ItemID.FEATHER, Calculations.random(1000, 2001)) // Withdraw between 1000-2000 feathers
            .setEnabled(() -> !Inventory.contains(ItemID.FEATHER))
            .setRefill(Calculations.random(3000, 4001)) // Buy between 3000-4000 feathers if we don't have enough for our withdraw amount
            .setLoadoutStrict(Inventory::isFull);

    public static final Map<Skill, LevelRange> FLY_FISHING_LEVEL_RANGES = Map.of(
            Skill.FISHING,    new LevelRange(20, 40)
    );
}
