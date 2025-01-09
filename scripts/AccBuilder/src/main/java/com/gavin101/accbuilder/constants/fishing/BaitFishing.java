package com.gavin101.accbuilder.constants.fishing;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class BaitFishing {
    public static EquipmentLoadout BAIT_FISHING_EQUIPMENT = new EquipmentLoadout();
    public static InventoryLoadout BAIT_FISHING_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.FISHING_ROD)
            .addReq(ItemID.FISHING_BAIT, 500)
            .setEnabled(() -> Inventory.count(ItemID.FISHING_BAIT) < 28)
            .setRefill(1000)
            .setLoadoutStrict(Inventory::isFull);
    public static final RectArea LUMBRIDGE_FISHING_AREA = new RectArea(3245, 3155, 3238, 3144);

    public static final Map<Skill, LevelRange> BAIT_FISHING_LEVEL_RANGES = Map.of(
            Skill.FISHING,    new LevelRange(5, 20)
    );
}
