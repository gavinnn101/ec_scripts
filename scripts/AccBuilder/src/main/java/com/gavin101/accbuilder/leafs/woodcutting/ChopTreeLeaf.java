package com.gavin101.accbuilder.leafs.woodcutting;

import com.gavin101.accbuilder.constants.woodcutting.Woodcutting;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;

@RequiredArgsConstructor
public class ChopTreeLeaf extends Leaf {
    private final String treeName;

    @Override
    public boolean isValid() {
        return !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        Log.info("Finding tree to chop: " +treeName);
        GameObject tree = GameObjects.closest(treeName);
        if (tree != null && tree.canReach()) {
            Log.debug("Found tree, interacting with it.");
            new EntityInteractEvent(tree, "Chop down").setEventCompleteCondition(
                    () -> Players.localPlayer().getAnimation() == Woodcutting.TREE_CHOPPING_ANIMATION, Calculations.random(1500, 3000)
            ).execute();
        }
        return ReactionGenerator.getAFK();
    }
}
