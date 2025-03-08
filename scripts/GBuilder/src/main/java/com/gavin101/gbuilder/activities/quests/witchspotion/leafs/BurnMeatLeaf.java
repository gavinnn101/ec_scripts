package com.gavin101.gbuilder.activities.quests.witchspotion.leafs;

import com.gavin101.gbuilder.activities.quests.witchspotion.constants.Constants;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.item.Item;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.walking.Walking;

public class BurnMeatLeaf extends Leaf {
    Item cookedMeat;
    @Override
    public boolean isValid() {
        cookedMeat = Inventory.get(ItemID.COOKED_MEAT);
        return Quest.WITCHS_POTION.isInProgress() && cookedMeat != null;
    }

    @Override
    public int onLoop() {
        GameObject range = GameObjects.closest("Range");
        if (range != null && range.canReach()) {
            Log.info("Burning meat on range.");
            new InventoryEvent(cookedMeat).on(range).setEventCompleteCondition(
                    () -> Inventory.contains(ItemID.BURNT_MEAT)
            ).execute();
        } else {
            Log.debug("Walking to range to burn meat.");
            Walking.walk(Constants.RANGE_TILE, () -> GameObjects.closest("Range") != null);
        }
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getNormal();
    }
}
