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
            Log.debug("Using shrimps on fire.");
            new InventoryEvent(Inventory.get(rawShrimpID)).on(fire).setEventCompleteCondition(
                    () -> !Inventory.contains(rawShrimpID), Calculations.random(2000, 5000)
            ).execute();
            }
        return ReactionGenerator.getNormal();
    }
}
