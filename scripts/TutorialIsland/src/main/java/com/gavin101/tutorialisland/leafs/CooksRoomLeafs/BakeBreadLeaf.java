package com.gavin101.tutorialisland.leafs.CooksRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.data.ObjectID;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;

public class BakeBreadLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 160;
    }

    @Override
    public int onLoop() {
        Log.info("Baking bread on the range.");
        GameObject range = GameObjects.closest(ObjectID.RANGE_9736);
        if (range != null && range.canReach()) {
            Log.debug("Using the range (Cook) to make bread dough.");
            new EntityInteractEvent(range, "Cook").setEventCompleteCondition(
                    () -> !Inventory.contains(ItemID.BREAD_DOUGH), Calculations.random(1500, 5000)).execute();
        }
        return ReactionGenerator.getNormal();
    }
}
