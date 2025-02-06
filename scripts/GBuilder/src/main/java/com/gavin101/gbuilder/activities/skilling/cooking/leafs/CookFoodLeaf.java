package com.gavin101.gbuilder.activities.skilling.cooking.leafs;

import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.widgets.WidgetChild;


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
        Log.info("Starting to cook food.");
        WidgetChild cookWidget = getCookWidget();
        if (cookWidget != null && cookWidget.isVisible()) {
            Log.debug("Cooking widget is visible, cooking food.");
            int initialFoodCount = getFoodCount(rawFoodID);
            new WidgetEvent(cookWidget, "Cook").setEventCompleteCondition(
                    () -> getFoodCount(rawFoodID) < initialFoodCount, Calculations.random(500, 1000)
            ).execute();
            MethodProvider.sleep(Calculations.random(400, 600));
        } else {
            GameObject cookingObject = GameObjects.closest(cookingObjectName);
            if (cookingObject != null && cookingObject.canReach()) {
                Log.debug("Interacting with cooking object: " +cookingObject.getName());
                new EntityInteractEvent(cookingObject, "Cook").setEventCompleteCondition(
                        () -> getCookWidget() != null, Calculations.random(500, 1500)
                ).execute();
            }
        }
        return ReactionGenerator.getNormal();
    }

    private int getFoodCount(int rawFoodID) {
        return Inventory.get(rawFoodID).getAmount();
    }

    private WidgetChild getCookWidget() {
        return Widgets.getWidgetChild(i ->
                // widget that has a parent (child widget) with action "Cook"
                i.getParent() != null
                && i.containsAction("Cook")
                // Filter out the cooking skill widget
                && !i.containsAction("Cooking")
        );
    }
}
