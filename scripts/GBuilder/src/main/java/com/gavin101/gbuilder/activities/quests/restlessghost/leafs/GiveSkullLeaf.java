package com.gavin101.gbuilder.activities.quests.restlessghost.leafs;

import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.item.Item;

public class GiveSkullLeaf extends Leaf {
    private static final int coffinId = 15061;
    private static GameObject coffin;
    private static Item ghostsSkull;
    @Override
    public boolean isValid() {
        coffin = GameObjects.closest(coffinId);
        ghostsSkull = Inventory.get(ItemID.GHOSTS_SKULL);
        return coffin != null && ghostsSkull != null;
    }

    @Override
    public int onLoop() {
        Log.info("Using ghost skull on coffin");
        if (coffin != null && ghostsSkull != null) {
            new InventoryEvent(ghostsSkull).on(coffin).setEventCompleteCondition(
                    () -> !Inventory.contains(ghostsSkull), Calculations.random(750, 1500)
            ).execute();
        }
        return FatigueTracker.getCurrentReactionTime();
    }
}
