package com.gavin101.gbuilder.activities.skilling.combat.leafs;

import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.item.Item;

public class BuryBonesLeaf extends Leaf {
    Item bonesToBury = null;

    @Override
    public boolean isValid() {
        Item bones = getInventoryBones();
        if (bones != null) {
            bonesToBury = bones;
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {
        Log.info("Burying bones in our inventory");
        if (bonesToBury != null) {
            new InventoryEvent(bonesToBury, "Bury").setEventCompleteCondition(
                    () -> false, Calculations.random(250, 1000)
            ).execute();
        }
//        return ReactionGenerator.getPredictable();
        return FatigueTracker.getCurrentReactionTime();
    }

    private static Item getInventoryBones() {
        return Inventory.get(i -> i.getName().toLowerCase().contains("bones"));
    }
}
