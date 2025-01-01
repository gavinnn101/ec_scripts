package com.gavin101.tutorialisland.leafs.CooksRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import com.gavin101.tutorialisland.GLib;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.tabs.Tab;

public class MakeDoughLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 150;
    }

    @Override
    public int onLoop() {
        Log.info("Making bread dough");
        GLib.openTab(Tab.INVENTORY);
        Log.debug("Combining flour with water to make bread dough.");
        new InventoryEvent(Inventory.get("Pot of flour")).on(Inventory.get("Bucket of water")
        ).setEventCompleteCondition(
                () -> Inventory.contains("Bread dough"), Calculations.random(1500, 3000)
        ).execute();
        return ReactionGenerator.getNormal();
    }
}
