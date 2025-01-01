package com.gavin101.accbuilder.leafs.combat;

import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.item.ItemComposite;
import net.eternalclient.api.wrappers.skill.Skill;

public class EatFoodLeaf extends Leaf {
    private final int foodToEatID;
    private final int healthThreshold;

    public EatFoodLeaf(int foodToEatID, int healthThreshold) {
        this.foodToEatID = foodToEatID;
        this.healthThreshold = healthThreshold;
    }

    @Override
    public boolean isValid() {
        return Skills.getBoostedLevels(Skill.HITPOINTS) < healthThreshold && Inventory.contains(foodToEatID);
    }

    @Override
    public int onLoop() {
        Log.info("Eating food: " + ItemComposite.getItem(foodToEatID).getName());
        int originalHp = Skills.getBoostedLevels(Skill.HITPOINTS);
        new InventoryEvent(foodToEatID, "Eat").setEventCompleteCondition(
                () -> Skills.getBoostedLevels(Skill.HITPOINTS) > originalHp, Calculations.random(350, 500)
        ).execute();
        return ReactionGenerator.getNormal();
    }
}
