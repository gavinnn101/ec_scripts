package com.gavin101.gbuilder.activities.skilling.combat;

import com.gavin101.GLib.GLib;
import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.gbuilder.activities.skilling.combat.constants.*;
import com.gavin101.gbuilder.activities.skilling.combat.leafs.EatFoodLeaf;
import com.gavin101.gbuilder.activities.skilling.combat.leafs.FightMonsterLeaf;
import com.gavin101.gbuilder.activities.skilling.combat.leafs.SetAttackStyleLeaf;
import com.gavin101.gbuilder.activities.skilling.combat.leafs.SetBestCombatEquipmentLeaf;
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
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
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
                ItemID.SHRIMPS, // We should make food optional
                Chickens.CHICKEN_LOOT,
                GLib.getRandomArea(Chickens.CHICKEN_COMBAT_AREAS)
        ),
        COWS(
                "Cow",
                10,
                20,
                Cows.COW_INVENTORY,
                ItemID.HERRING,
                Cows.COW_LOOT,
                GLib.getRandomArea(Cows.COW_COMBAT_AREAS)
        ),
        BARBARIANS(
                "Barbarian",
                20,
                30,
                Barbarians.BARBARIAN_INVENTORY,
                ItemID.TROUT,
                Common.EMPTY_LOOT,
                Barbarians.BARBARIAN_AREA
        ),
        ALKHARIDWARRIORS(
                "Al Kharid warrior",
                30,
                40,
                AlkharidWarriors.ALKHARID_WARRIOR_INVENTORY,
                ItemID.TROUT,
                Common.EMPTY_LOOT,
                AlkharidWarriors.ALKHARID_WARRIOR_AREA

        ),
        FLESHCRAWLERS(
                "Flesh Crawler",
                40,
                99,
                FleshCrawlers.FLESH_CRAWLERS_INVENTORY,
                ItemID.LOBSTER,
                Common.EMPTY_LOOT,
                GLib.getRandomArea(FleshCrawlers.FLESH_CRAWLERS_AREAS)
        );

        private final String name;
        private final int minLevel;
        private final int maxLevel;
        // No equipment loadout because we're separately checking if we're using the best equipment for our level.
        private final InventoryLoadout inventoryLoadout;
        private final int foodId;
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
                .inventoryLoadout(monsterTier.getInventoryLoadout())
                .minLevel(monsterTier.getMinLevel())
                .maxLevel(monsterTier.getMaxLevel())
                .validator(() -> canStartMonsterTier(monsterTier))
                .build();
    }

    private static Branch createBranch(MonsterTier monsterTier, CombatType combatType, String activityName) {
        return new ValidateActivityBranch(ActivityManager.getActivity(activityName)).addLeafs(
                new SetBestCombatEquipmentLeaf(),
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(true)
                        .build(),
                new EatFoodLeaf(monsterTier.getFoodId()),
                new IsAfkBranch().addLeafs(
                        LootItemsLeaf.builder().itemIds(monsterTier.getLoot()).build(),
                        new SetAttackStyleLeaf(combatType.getAttackStyle()),
                        new FightMonsterLeaf(monsterTier.getName(), monsterTier.getArea())
                )
        );
    }

    private static boolean canStartMonsterTier(MonsterTier monsterTier) {
        // Don't start a monster tier if all combat stats don't meet that tiers minimum level.
        int attackLevel = Skills.getRealLevel(Skill.ATTACK);
        int strengthLevel = Skills.getRealLevel(Skill.STRENGTH);
        int defenseLevel = Skills.getRealLevel(Skill.DEFENCE);
        return (attackLevel >= monsterTier.getMinLevel()
                && strengthLevel >= monsterTier.getMinLevel()
                && defenseLevel >= monsterTier.getMinLevel()
        );
    }
}
