package com.gavin101.gbuilder.activities.skilling.cooking.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.processing.Production;


@RequiredArgsConstructor
public class CookFoodLeaf extends Leaf {
    private final String cookingObjectName;
    private final int rawFoodID;

    @Override
    public boolean isValid() {
        return (Inventory.contains(rawFoodID));
    }

    @Override
    public int onLoop() {
        Log.info("Cooking food: " + GLib.getItemName(rawFoodID));
        if (Production.isOpen()) {
            Log.debug("Production is open, cooking all food.");
            int initialFoodCount = getFoodCount(rawFoodID);
            String cookedFoodName = GLib.getItemName(rawFoodID);
            if (cookedFoodName != null) {
                cookedFoodName = cookedFoodName.replace("Raw ", "");
                if (Production.interact(rawFoodID)) {
                    Log.debug("Interacted with production, waiting for food to be cooked.");
                    MethodProvider.sleepUntil(() -> getFoodCount(rawFoodID) < initialFoodCount, Calculations.random(500, 2500));
                }
            }
        } else {
            GameObject cookingObject = GameObjects.closest(cookingObjectName);
            if (cookingObject != null && cookingObject.canReach()) {
                Log.debug("Interacting with cooking object: " +cookingObject.getName());
                new EntityInteractEvent(cookingObject, "Cook").setEventCompleteCondition(
                        Production::isOpen, Calculations.random(500, 2500)
                ).execute();
            }
        }
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getNormal();
    }

    private int getFoodCount(int rawFoodID) {
        return Inventory.count(rawFoodID);
    }
}
