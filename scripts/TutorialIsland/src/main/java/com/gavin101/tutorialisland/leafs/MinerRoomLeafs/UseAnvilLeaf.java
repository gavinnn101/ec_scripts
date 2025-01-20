package com.gavin101.tutorialisland.leafs.MinerRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;

public class UseAnvilLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int progress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return (progress == 340 || progress == 350)
                && !GLib.isWidgetValid(Widgets.getWidget(Constants.SMITHING_PARENT_ID));
    }

    @Override
    public int onLoop() {
        Log.info("Opening anvil menu.");
        GameObject anvil = GameObjects.closest("Anvil");
        if (anvil != null && anvil.canReach()) {
            Log.debug("Clicking 'Smith' on anvil.");
            new EntityInteractEvent(anvil, "Smith").setEventCompleteCondition(
                    () -> GLib.isWidgetValid(Widgets.getWidget(Constants.SMITHING_PARENT_ID)), Calculations.random(1500, 3500)
            ).execute();
        }
        return ReactionGenerator.getPredictable();
    }
}
