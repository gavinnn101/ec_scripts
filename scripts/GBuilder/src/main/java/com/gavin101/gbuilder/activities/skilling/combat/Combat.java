package com.gavin101.gbuilder.activities.skilling.combat;

import com.gavin101.GLib.GLib;
import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.gbuilder.activities.skilling.combat.constants.*;
import com.gavin101.gbuilder.activities.skilling.combat.leafs.FightMonsterLeaf;
import com.gavin101.gbuilder.activities.skilling.combat.leafs.SetAttackStyleLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.SkillActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import com.gavin101.gbuilder.utility.constants.Common;
import com.gavin101.gbuilder.utility.leafs.LootItemsLeaf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.AttackStyle;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.skill.Skill;

public class Combat {
    @Getter
    @RequiredArgsConstructor
    public enum CombatType {
        ATTACK("Attack training", AttackStyle.ACCURATE, Skill.ATTACK),
        STRENGTH("Strength training", AttackStyle.AGGRESSIVE, Skill.STRENGTH),
        DEFENCE("Defence training", AttackStyle.DEFENSIVE, Skill.DEFENCE);

        private final String description;
        private final AttackStyle attackStyle;
        private final Skill skill;
    }

    @Getter
    @RequiredArgsConstructor
    public enum MonsterTier {
        CHICKENS(
                "Chicken",
                1,
                10,
                Common.EMPTY_INVENTORY_LOADOUT,
                Chickens.CHICKEN_LOOT,
                GLib.getRandomArea(Chickens.CHICKEN_COMBAT_AREAS)
        ),
        COWS(
                "Cow",
                10,
                20,
                Cows.COW_INVENTORY,
                Cows.COW_LOOT,
                GLib.getRandomArea(Cows.COW_COMBAT_AREAS)
        ),
        BARBARIANS(
                "Barbarian",
                20,
                30,
                Barbarians.BARBARIAN_INVENTORY,
                Common.EMPTY_LOOT,
                Barbarians.BARBARIAN_AREA
        ),
        ALKHARIDWARRIORS(
                "Al Kharid warrior",
                40,
                50,
                AlkharidWarriors.ALKHARID_WARRIOR_INVENTORY,
                Common.EMPTY_LOOT,
                AlkharidWarriors.ALKHARID_WARRIOR_AREA

        ),
        FLESHCRAWLERS(
                "Flesh Crawler",
                50,
                99,
                FleshCrawlers.FLESH_CRAWLERS_INVENTORY,
                Common.EMPTY_LOOT,
                GLib.getRandomArea(FleshCrawlers.FLESH_CRAWLERS_AREAS)
        );

        private final String name;
        private final int minLevel;
        private final int maxLevel;
        // No equipment loadout because we're separately checking if we're using the best equipment for our level.
        private final InventoryLoadout inventoryLoadout;
        private final int[] loot;
        private final Area area;

        public void register() {
            for (CombatType combatType : CombatType.values()) {
                SkillActivity activity = Combat.createActivity(this, combatType);
                ActivityManager.registerActivity(activity);
            }
        }
    }

    public static SkillActivity createActivity(MonsterTier monsterTier, CombatType combatType) {
        String activityName = String.format("Fighting %ss (%s)", monsterTier.getName(), combatType.getDescription());

        return SkillActivity.builder()
                .name(activityName)
                .branchSupplier(() -> createBranch(monsterTier, combatType, activityName))
                .activitySkill(combatType.getSkill())
                .equipmentLoadout(getBestCombatEquipment())
                .inventoryLoadout(monsterTier.getInventoryLoadout())
                .minLevel(monsterTier.getMinLevel())
                .maxLevel(monsterTier.getMaxLevel())
                .build();
    }

    private static Branch createBranch(MonsterTier monsterTier, CombatType combatType, String activityName) {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(true)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new LootItemsLeaf(monsterTier.getLoot(), 3),
                        new SetAttackStyleLeaf(combatType.getAttackStyle()),
                        new FightMonsterLeaf(monsterTier.getName(), monsterTier.getArea())
                )
        );
    }

    public static EquipmentLoadout getBestCombatEquipment() {
        int defenseLevel = Skills.getRealLevel(Skill.DEFENCE);
        int attackLevel = Skills.getRealLevel(Skill.ATTACK);

        EquipmentLoadout loadout = new EquipmentLoadout()
                .addAmulet(ItemID.AMULET_OF_POWER);

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
        } else if (defenseLevel >= 40) {
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
        } else if (attackLevel >= 40) {
            loadout.addWeapon(ItemID.RUNE_SCIMITAR);
        }
        return loadout;
    }
}
