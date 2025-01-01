package com.gavin101.accbuilder.leafs.fishing;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;

public class DropFishLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Inventory.isFull();
    }

    @Override
    public int onLoop() {
        Log.info("Dropping fish from inventory.");
        if (Inventory.dropAll(i -> i.containsName("Raw"))) {
            MethodProvider.sleepUntil(
                    () -> Inventory.contains(i -> !i.containsName("Raw")), Calculations.random(1000, 2000)
            );
        }
        return ReactionGenerator.getNormal();
    }
}
