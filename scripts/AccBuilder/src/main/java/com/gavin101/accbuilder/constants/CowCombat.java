package com.gavin101.accbuilder.constants;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class CowCombat {
    public static EquipmentLoadout COW_EQUIPMENT = new EquipmentLoadout()
            .addHat(ItemID.STEEL_FULL_HELM)
            .addChest(ItemID.STEEL_PLATEBODY)
            .addLegs(ItemID.STEEL_PLATELEGS)
            .addShield(ItemID.STEEL_KITESHIELD)
            .addWeapon(ItemID.STEEL_SCIMITAR)
            .addAmulet(ItemID.AMULET_OF_POWER)
            .setStrict(true);

    public static InventoryLoadout COW_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.TROUT, 10)
            .setLoadoutStrict(() -> !Inventory.contains(ItemID.TROUT));

    public static final RectArea COW_AREA = new RectArea(3194, 3300, 3209, 3285);

    public static final Map<Skill, Integer> COW_LEVEL_GOALS = Map.of(
            Skill.ATTACK, 20,
            Skill.STRENGTH, 50,
            Skill.DEFENCE, 20
    );

    public static String[] COW_LOOT = {
            "Cowhide"
    };
}
