package com.gavin101.accbuilder.leafs.combat;

import net.eternalclient.api.accessors.GroundItems;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GroundItem;

public class LootItemsLeaf extends Leaf {
    private final String[] itemsToLoot;
    private final int maxDistance;
    private static GroundItem currentLootItem;

    public LootItemsLeaf(String[] itemsToLoot, int maxDistance) {
        this.itemsToLoot = itemsToLoot;
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean isValid() {
        if (Inventory.isFull()) {
            return false;
        }

        GroundItem loot = findValidLoot();
        if (loot != null) {
            currentLootItem = loot;
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {
        Log.info("Looting item: " +currentLootItem.getName());
        new EntityInteractEvent(currentLootItem, "Take").setEventCompleteCondition(
                () -> currentLootItem == null, Calculations.random(250, 1500)
        ).execute();
        return ReactionGenerator.getPredictable();
    }

    public GroundItem findValidLoot() {
        return GroundItems.closest(i ->
                i.hasName(itemsToLoot)
                        && i.hasAction("Take")
                        && i.distanceTo(Players.localPlayer()) <= maxDistance
                        && i.canReach()
        );
    }
}
