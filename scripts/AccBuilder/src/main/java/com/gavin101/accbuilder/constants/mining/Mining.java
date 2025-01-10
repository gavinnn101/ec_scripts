package com.gavin101.accbuilder.constants.mining;

import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.skill.Skill;

public class Mining {
    public static EquipmentLoadout MINING_EQUIPMENT = new EquipmentLoadout();
    public static InventoryLoadout MINING_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.STEEL_PICKAXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.MINING) >= 6 && Skills.getRealLevel(Skill.MINING) < 21)
            .addReq(ItemID.MITHRIL_PICKAXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.MINING) >= 21 && Skills.getRealLevel(Skill.MINING) < 31)
            .addReq(ItemID.ADAMANT_PICKAXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.MINING) >= 31 && Skills.getRealLevel(Skill.MINING) < 41)
            .addReq(ItemID.RUNE_PICKAXE)
            .setEnabled(() -> Skills.getRealLevel(Skill.MINING) >= 41)
            .setLoadoutStrict(Inventory::isFull);

    public static final WorldTile LUMBRIDGE_TIN_TILE = new WorldTile(3223, 3147, 0);
    public static final WorldTile RIMMINGTON_IRON_TILE = new WorldTile(2969, 3241, 0);
}
