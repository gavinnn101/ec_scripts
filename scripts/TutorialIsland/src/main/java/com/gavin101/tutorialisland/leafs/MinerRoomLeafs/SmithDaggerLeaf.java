package com.gavin101.tutorialisland.leafs.MinerRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.widgets.WidgetChild;

public class SmithDaggerLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 350;
    }

    @Override
    public int onLoop() {
        Log.info("Smithing bronze dagger.");
        WidgetChild daggerWidget = Widgets.getWidgetChild(Constants.SMITHING_PARENT_ID, Constants.SMITHING_DAGGER_ID);
        if (daggerWidget != null && daggerWidget.isVisible()) {
            Log.debug("Selecting 'Smith' on dagger widget.");
            new WidgetEvent(daggerWidget, "Smith").setEventCompleteCondition(
                    () -> Inventory.contains("Bronze dagger"), Calculations.random(1500, 3500)
            ).execute();
        }
        return ReactionGenerator.getNormal();
    }
}
