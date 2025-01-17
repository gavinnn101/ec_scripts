package com.gavin101.tutorialisland.leafs.CombatRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.widgets.WidgetChild;

public class OpenEquipmentInterfaceLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 400;
    }

    @Override
    public int onLoop() {
        Log.info("Opening equipment interface in equipment tab.");
        WidgetChild equipmentButtonWidget = Widgets.getWidgetChild(Constants.EQUIPMENT_TAB_PARENT_ID, Constants.EQUIPMENT_TAB_EQUIPMENT_INTERFACE_BUTTON_ID);
        if (GLib.isWidgetValid(equipmentButtonWidget)) {
            Log.debug("Interacting 'View equipment stats' with equipment interface button.");
            new WidgetEvent(equipmentButtonWidget, "View equipment stats").setEventCompleteCondition(
                    () -> GLib.isWidgetValid(Widgets.getWidget(Constants.EQUIPMENT_INTERFACE_PARENT_ID)), Calculations.random(1500, 3000)
            ).execute();
        }
        return ReactionGenerator.getNormal();
    }
}
