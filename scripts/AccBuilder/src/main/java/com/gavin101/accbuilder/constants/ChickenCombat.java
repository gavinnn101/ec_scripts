package com.gavin101.accbuilder.constants;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class ChickenCombat {
    public static EquipmentLoadout CHICKEN_EQUIPMENT = new EquipmentLoadout()
            .addHat(ItemID.IRON_FULL_HELM)
            .addChest(ItemID.IRON_PLATEBODY)
            .addLegs(ItemID.IRON_PLATELEGS)
            .addShield(ItemID.IRON_KITESHIELD)
            .addWeapon(ItemID.IRON_SCIMITAR)
            .addAmulet(ItemID.AMULET_OF_POWER)
            .setStrict(true);

    public static InventoryLoadout CHICKEN_INVENTORY = new InventoryLoadout();
//            .addReq(ItemID.TROUT, 10)
//            .setEnabled(() -> !Inventory.contains(ItemID.TROUT))
//            .setLoadoutStrict(() -> !Inventory.contains(ItemID.TROUT));

    public static final RectArea CHICKEN_AREA = new RectArea(3172, 3302, 3183, 3289);

    public static final Map<Skill, Integer> CHICKEN_LEVEL_GOALS = Map.of(
            Skill.ATTACK, 10,
            Skill.STRENGTH, 10,
            Skill.DEFENCE, 10
    );

    public static final String[] CHICKEN_LOOT = {
            "Feathers"
    };
}
