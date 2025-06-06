package com.gavin101.gbuilder.activities.quests.goblindiplomacy.leafs;

import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.item.Item;

public class MakeArmorLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Inventory.containsAll(ItemID.BLUE_DYE, ItemID.GOBLIN_MAIL)
                || Inventory.containsAll(ItemID.ORANGE_DYE, ItemID.GOBLIN_MAIL);
    }

    @Override
    public int onLoop() {
        Item goblinMail = Inventory.get(ItemID.GOBLIN_MAIL);

        Item blueDye = Inventory.get(ItemID.BLUE_DYE);
        if (blueDye != null && goblinMail != null) {
            new InventoryEvent(blueDye).on(goblinMail).setEventCompleteCondition(
                    () -> Inventory.contains(ItemID.BLUE_GOBLIN_MAIL), Calculations.random(750, 1500)
            ).execute();
            return FatigueTracker.getCurrentReactionTime();
//            ReactionGenerator.getPredictable()
        }

        Item orangeDye = Inventory.get(ItemID.ORANGE_DYE);
        if (orangeDye != null && goblinMail != null) {
            new InventoryEvent(orangeDye).on(goblinMail).setEventCompleteCondition(
                    () -> Inventory.contains(ItemID.ORANGE_GOBLIN_MAIL), Calculations.random(750, 1500)
            ).execute();
        }
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getPredictable();
    }
}
