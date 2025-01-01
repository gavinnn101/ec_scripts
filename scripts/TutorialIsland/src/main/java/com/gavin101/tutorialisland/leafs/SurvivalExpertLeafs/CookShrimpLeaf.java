package com.gavin101.tutorialisland.leafs.SurvivalExpertLeafs;

import com.gavin101.tutorialisland.Constants;
import com.gavin101.tutorialisland.GLib;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.tabs.Tab;

public class CookShrimpLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 90;
    }

    @Override
    public int onLoop() {
        Log.info("Cooking shrimps on fire");
        GameObject fire = GameObjects.closest("Fire");
        if (fire != null && fire.canReach()) {
            GLib.openTab(Tab.INVENTORY);
            Log.debug("Using shrimps on fire.");
            new InventoryEvent(Inventory.get("Raw shrimps")).on(fire).setEventCompleteCondition(
                    () -> !Inventory.contains("Raw shrimps"), Calculations.random(2000, 5000)
            ).execute();
            }
        return ReactionGenerator.getPredictable();
    }
}
