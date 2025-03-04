package com.gavin101.tutorialisland.leafs.CooksRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.item.Item;

public class MakeDoughLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 150;
    }

    @Override
    public int onLoop() {
        Log.info("Making bread dough");
        Item potOfFlour = Inventory.get(ItemID.POT_OF_FLOUR_2516);
        Item bucketOfWater = Inventory.get(ItemID.BUCKET_OF_WATER);
        if (potOfFlour != null && bucketOfWater != null) {
            new InventoryEvent(potOfFlour).on(bucketOfWater).setEventCompleteCondition(
                    () -> Inventory.contains(ItemID.BREAD_DOUGH), Calculations.random(1500, 3000)
            ).execute();
        }
        return ReactionGenerator.getNormal();
    }
}
