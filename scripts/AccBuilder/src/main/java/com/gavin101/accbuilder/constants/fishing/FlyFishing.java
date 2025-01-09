package com.gavin101.accbuilder.constants.fishing;

import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class FlyFishing {
    public static EquipmentLoadout FLY_FISHING_EQUIPMENT = new EquipmentLoadout();
    public static InventoryLoadout FLY_FISHING_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.FLY_FISHING_ROD)
            .addReq(ItemID.FEATHER, 2000)
            .setEnabled(() -> Inventory.count(ItemID.FEATHER) < 28)
            .setRefill(4000)
            .setLoadoutStrict(Inventory::isFull);
    public static final RectArea BARB_FLY_FISHING_AREA = new RectArea(3110, 3436, 3101, 3423);
    public static final Map<Skill, LevelRange> FLY_FISHING_LEVEL_RANGES = Map.of(
            Skill.FISHING,    new LevelRange(20, 40)
    );
}
