package com.gavin101.accbuilder.leafs.combat;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.item.ItemComposite;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Arrays;

public class SetBestCombatEquipmentLeaf extends Leaf {
    private static EquipmentLoadout bestCombatEquipment;
    @Override
    public boolean isValid() {
        String[] equipment = Players.localPlayer().getEquipmentNames();
        bestCombatEquipment = getBestCombatEquipment();

        if (Main.currentEquipmentLoadout == null) {
            return true;
        }

        if (GLib.equipmentLoadoutsAreEqual(Main.currentEquipmentLoadout, bestCombatEquipment)) {
            return false;
        }

        return bestCombatEquipment.getItems().stream()
                .anyMatch(item -> !Arrays.asList(equipment).contains(item.getName()));
    }

    @Override
    public int onLoop() {
        Log.info("Updating best combat equipment (Main.bestEquipment)");
        Log.info("Updating to equipmentLoadout: " +bestCombatEquipment.getItems());
        Main.currentEquipmentLoadout = bestCombatEquipment;
        return ReactionGenerator.getNormal();
    }

    public static EquipmentLoadout getBestCombatEquipment() {
        int defenseLevel = Skills.getRealLevel(Skill.DEFENCE);
        int attackLevel = Skills.getRealLevel(Skill.ATTACK);

        EquipmentLoadout loadout = new EquipmentLoadout()
                .addAmulet(ItemID.AMULET_OF_POWER)
                .setStrict(true);

        if (defenseLevel >= 1 && defenseLevel < 5) {
            loadout.addHat(ItemID.IRON_FULL_HELM);
            loadout.addChest(ItemID.IRON_PLATEBODY);
            loadout.addLegs(ItemID.IRON_PLATELEGS);
            loadout.addShield(ItemID.IRON_KITESHIELD);
        } else if (defenseLevel >= 5 && defenseLevel < 20) {
            loadout.addHat(ItemID.STEEL_FULL_HELM);
            loadout.addChest(ItemID.STEEL_PLATEBODY);
            loadout.addLegs(ItemID.STEEL_PLATELEGS);
            loadout.addShield(ItemID.STEEL_KITESHIELD);
        } else if (defenseLevel >= 20 && defenseLevel < 30) {
            loadout.addHat(ItemID.MITHRIL_FULL_HELM);
            loadout.addChest(ItemID.MITHRIL_PLATEBODY);
            loadout.addLegs(ItemID.MITHRIL_PLATELEGS);
            loadout.addShield(ItemID.MITHRIL_KITESHIELD);
        } else if (defenseLevel >= 30 && defenseLevel < 40) {
            loadout.addHat(ItemID.ADAMANT_FULL_HELM);
            loadout.addChest(ItemID.ADAMANT_PLATEBODY);
            loadout.addLegs(ItemID.ADAMANT_PLATELEGS);
            loadout.addShield(ItemID.ADAMANT_KITESHIELD);
        } else if (defenseLevel >= 40 && defenseLevel < 60) {
            loadout.addHat(ItemID.RUNE_FULL_HELM);
            loadout.addChest(ItemID.RUNE_CHAINBODY);
            loadout.addLegs(ItemID.RUNE_PLATELEGS);
            loadout.addShield(ItemID.RUNE_KITESHIELD);
        }

        if (attackLevel >= 1 && attackLevel < 5) {
            loadout.addWeapon(ItemID.IRON_SCIMITAR);
        } else if (attackLevel >= 5 && attackLevel < 20) {
            loadout.addWeapon(ItemID.STEEL_SCIMITAR);
        } else if (attackLevel >= 20 && attackLevel < 30) {
            loadout.addWeapon(ItemID.MITHRIL_SCIMITAR);
        } else if (attackLevel >= 30 && attackLevel < 40) {
            loadout.addWeapon(ItemID.ADAMANT_SCIMITAR);
        } else if (attackLevel >= 40 && attackLevel < 60) {
            loadout.addWeapon(ItemID.RUNE_SCIMITAR);
        }
        return loadout;
    }
}
