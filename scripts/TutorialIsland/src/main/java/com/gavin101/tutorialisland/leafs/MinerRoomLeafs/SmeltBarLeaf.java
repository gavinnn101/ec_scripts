package com.gavin101.tutorialisland.leafs.MinerRoomLeafs;

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

public class SmeltBarLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 320;
    }

    @Override
    public int onLoop() {
        Log.info("Smelting ores into a bronze bar.");
        GameObject furnace = GameObjects.closest(ObjectID.FURNACE_10082);
        if (furnace != null && furnace.canReach()) {
            Log.debug("Interacting with furnace: `Use`.");
            new EntityInteractEvent(furnace, "Use").setEventCompleteCondition(
                    () -> Inventory.contains(ItemID.BRONZE_BAR), Calculations.random(2500, 5000)
            ).execute();
        }
        return ReactionGenerator.getNormal();
    }
}
