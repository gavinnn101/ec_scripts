package com.gavin101.accbuilder.constants;

import net.eternalclient.api.accessors.AttackStyle;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class Common {
    public static final Map<Skill, AttackStyle> SKILL_TO_ATTACK_STYLE = Map.of(
            Skill.ATTACK, AttackStyle.ACCURATE,
            Skill.STRENGTH, AttackStyle.AGGRESSIVE,
            Skill.DEFENCE, AttackStyle.DEFENSIVE
    );

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
        }

        if (attackLevel >= 1 && attackLevel < 5) {
            loadout.addWeapon(ItemID.IRON_SCIMITAR);
        } else if (attackLevel >= 5 && attackLevel < 20) {
            loadout.addWeapon(ItemID.STEEL_SCIMITAR);
        } else if (attackLevel >= 20 && attackLevel < 30) {
            loadout.addWeapon(ItemID.MITHRIL_SCIMITAR);
        } else if (attackLevel >= 30 && attackLevel < 40) {
            loadout.addWeapon(ItemID.ADAMANT_SCIMITAR);
        }
        return loadout;
    }
}
