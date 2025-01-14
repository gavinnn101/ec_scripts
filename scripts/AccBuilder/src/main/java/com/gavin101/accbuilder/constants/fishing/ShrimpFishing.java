package com.gavin101.accbuilder.constants.fishing;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class ShrimpFishing {
    public static EquipmentLoadout SHRIMP_EQUIPMENT = new EquipmentLoadout();

    public static InventoryLoadout SHRIMP_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.SMALL_FISHING_NET)
            .setLoadoutStrict(Inventory::isFull);

    public static final Map<Skill, LevelRange> SHRIMP_FISHING_LEVEL_RANGES = Map.of(
            Skill.FISHING,    new LevelRange(1, 5)
    );
}
