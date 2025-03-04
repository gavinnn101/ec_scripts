package com.gavin101.tutorialisland.leafs.SurvivalExpertLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.data.ObjectID;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.item.Item;

public class CookShrimpLeaf extends Leaf {
    int rawShrimpID = ItemID.RAW_SHRIMPS_2514;
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 90;
    }

    @Override
    public int onLoop() {
        Log.info("Cooking shrimps on fire");
        GameObject fire = GameObjects.closest(ObjectID.FIRE_26185);
        if (fire != null && fire.canReach()) {
            Log.debug("Cooking shrimp on fire.");
            Item shrimp = Inventory.get(rawShrimpID);
            if (shrimp != null) {
                new InventoryEvent(shrimp).on(fire).setEventCompleteCondition(
                        () -> false, Calculations.random(2000, 5000)
                ).execute();
            }
        }
        return ReactionGenerator.getNormal();
    }
}
