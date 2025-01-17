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

public class CloseEquipmentInterfaceLeaf extends Leaf {
    private WidgetChild closeEquipmentInterfaceButtonWidget = null;

    @Override
    public boolean isValid() {
        if (closeEquipmentInterfaceButtonWidget == null) {
            closeEquipmentInterfaceButtonWidget = Widgets.getWidgetChild(
                    Constants.EQUIPMENT_INTERFACE_PARENT_ID,
                    Constants.EQUIPMENT_INTERFACE_CHILD_ID,
                    Constants.EQUIPMENT_INTERFACE_X_BUTTON_ID
            );
        }
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 410
                && (GLib.isWidgetValid(closeEquipmentInterfaceButtonWidget));
    }

    @Override
    public int onLoop() {
        Log.info("Closing equipment interface.");
        if (closeEquipmentInterfaceButtonWidget != null && closeEquipmentInterfaceButtonWidget.isVisible()) {
            new WidgetEvent(closeEquipmentInterfaceButtonWidget, "Close").setEventCompleteCondition(
                    () -> !closeEquipmentInterfaceButtonWidget.isVisible(), Calculations.random(1000, 2500)
            ).execute();
        }
        return ReactionGenerator.getNormal();
    }
}
