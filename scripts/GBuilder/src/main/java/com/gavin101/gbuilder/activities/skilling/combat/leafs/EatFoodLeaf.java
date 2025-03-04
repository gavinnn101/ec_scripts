package com.gavin101.gbuilder.activities.skilling.combat.leafs;

import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.item.Item;
import net.eternalclient.api.wrappers.skill.Skill;

@RequiredArgsConstructor
public class EatFoodLeaf extends Leaf {
    private final int foodToEatID;
    private static Item foodToEat;
    private static int currentHp;

    @Override
    public boolean isValid() {
        int healthThreshold = getRandomHealthThreshold(foodToEatID);
        foodToEat = Inventory.get(foodToEatID);
        currentHp = Skills.getBoostedLevels(Skill.HITPOINTS);
        return currentHp < healthThreshold && foodToEat != null;
    }

    @Override
    public int onLoop() {
        Log.info(String.format("Eating food: %s at hp: %s", foodToEat.getName(), currentHp));
        new InventoryEvent(foodToEat, "Eat").setEventCompleteCondition(
                () -> foodToEat == null, Calculations.random(350, 1000)
        ).execute();
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getNormal();
    }

    private int getRandomHealthThreshold(int foodToEatID) {
        int fullHp = Skills.getRealLevel(Skill.HITPOINTS);
        int foodHealth = foodToHealthMap(foodToEatID);
        int maxHpToEatAt = (fullHp - foodHealth) + 1;
        int randomVariation = Calculations.random(-3, 4);
        return Math.max(5, Math.min(maxHpToEatAt + randomVariation, maxHpToEatAt));
    }

    private int foodToHealthMap(int foodToEatID) {
        switch (foodToEatID) {
            case ItemID.SHRIMPS:
                return 3;
            case ItemID.ANCHOVIES:
                return 1;
            case ItemID.SARDINE:
                return 4;
            case ItemID.SALMON:
                return 9;
            case ItemID.TROUT:
                return 7;
            case ItemID.HERRING:
                return 5;
            case ItemID.PIKE:
                return 8;
            case ItemID.TUNA:
                return 10;
            case ItemID.SWORDFISH:
                return 14;
            case ItemID.LOBSTER:
                return 12;
        }
        // TODO: should probably throw an exception instead?
        return 1;
    }
}
