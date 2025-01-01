package com.gavin101.tutorialisland.leafs.GuidesRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.widgets.Widget;
import net.eternalclient.api.wrappers.widgets.WidgetChild;

public class CustomizeCharacterLeaf extends Leaf {

    private static final int[] CUSTOMIZATION_WIDGET_IDS = {
            16, 20, 24, 28, 32, 36, 40, 47, 51, 55, 59, 63
    };

    @Override
    public boolean isValid() {
        Widget customizeCharacterWidget = Widgets.getWidget(Constants.CUSTOMIZE_CHARACTER_PARENT_ID);
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 1
                && customizeCharacterWidget != null
                && customizeCharacterWidget.isVisible();
    }

    @Override
    public int onLoop() {
        Log.info("Customizing character.");
        for (int i : CUSTOMIZATION_WIDGET_IDS) {
            WidgetChild widgetChild = Widgets.getWidgetChild(Constants.CUSTOMIZE_CHARACTER_PARENT_ID, i);
            if (widgetChild != null && widgetChild.isVisible()) {
                int clickAmount = Calculations.random(0, 6);
                for (int clicks = 0; clickAmount > clicks; clicks++) {
                    new WidgetEvent(widgetChild, "Select").execute();
                    MethodProvider.sleep(Calculations.random(350, 900));
                }
            }
        }
        Log.info("Confirming character customization screen.");
        WidgetChild confirmCustomizationWidget = Widgets.getWidgetChild(Constants.CUSTOMIZE_CHARACTER_PARENT_ID, Constants.CUSTOMIZE_CHARACTER_CONFIRM_ID);
        if (confirmCustomizationWidget != null && confirmCustomizationWidget.isVisible()) {
            Log.debug("Clicking 'Confirm' button on character customization screen.");
            new WidgetEvent(confirmCustomizationWidget, "Confirm").setEventCompleteCondition(
                    () -> !confirmCustomizationWidget.isVisible(), Calculations.random(2000, 3500)).execute();
        }
        return ReactionGenerator.getPredictable();
    }
}
