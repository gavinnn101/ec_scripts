package com.gavin101.GLib.leafs.common;

import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.widgets.WidgetChild;

public class CloseChatLeaf extends Leaf {
    private static final int chatBoxParentId = 162;
    private static final int nameChildWidgetId = 56;
    private static final int allButtonChildWidgetId = 4;

    @Override
    public boolean isValid() {
        WidgetChild chatNameWidget = getChatNameWidget();
        return chatNameWidget != null && chatNameWidget.isVisible();
    }

    @Override
    public int onLoop() {
        Log.info("Closing chat box");
        WidgetChild allButton = getAllButtonWidget();
        if (allButton != null && allButton.isVisible()) {
            new WidgetEvent(allButton).setEventCompleteCondition(
                    () -> !getChatNameWidget().isVisible(), Calculations.random(1000, 2500)
            ).execute();
        }
        return ReactionGenerator.getPredictable();
    }

    private WidgetChild getChatNameWidget() {
        return Widgets.getWidgetChild(chatBoxParentId, nameChildWidgetId);
    }

    private WidgetChild getAllButtonWidget() {
        return Widgets.getWidgetChild(chatBoxParentId, allButtonChildWidgetId);
    }
}
