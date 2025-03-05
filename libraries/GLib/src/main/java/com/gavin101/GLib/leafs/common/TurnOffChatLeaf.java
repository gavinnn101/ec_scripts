package com.gavin101.GLib.leafs.common;

import lombok.AllArgsConstructor;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.widgets.WidgetChild;


public class TurnOffChatLeaf extends Leaf {
    private static final int chatBoxParentId = 162;

    @AllArgsConstructor
    private enum ChatType {
        PUBLIC(14, getChatActionsWidgetId(14), "Public"),
        PRIVATE(18, getChatActionsWidgetId(18), "Private"),
        CHANNEL(22, getChatActionsWidgetId(22), "Channel"),
        CLAN(26, getChatActionsWidgetId(26), "Clan");

        private final int widgetStatusId;
        private final int widgetActionId;
        private final String name;
    }

    @Override
    public boolean isValid() {
        for (ChatType chatType : ChatType.values()) {
            WidgetChild chatStatusWidget = Widgets.getWidgetChild(chatBoxParentId, chatType.widgetStatusId);
            if (!isChatOff(chatStatusWidget)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int onLoop() {
        for (ChatType chatType : ChatType.values()) {
            WidgetChild chatStatusWidget = Widgets.getWidgetChild(chatBoxParentId, chatType.widgetStatusId);
            if (!isChatOff(chatStatusWidget)) {
                Log.info("Turning off chat: " +chatType.name);
                String actionString = String.format("<col=ffff00>%s:</col> Show none", chatType.name);
                WidgetChild actionWidget = Widgets.getWidgetChild(chatBoxParentId, chatType.widgetActionId);
                if (actionWidget != null && actionWidget.isVisible()) {
                    new WidgetEvent(actionWidget, actionString).setEventCompleteCondition(
                            () -> isChatOff(chatStatusWidget), Calculations.random(500, 1500)
                    ).execute();
                    return ReactionGenerator.getPredictable();
                }
            }
        }
        return ReactionGenerator.getPredictable();
    }

    private static boolean isChatOff(WidgetChild chatWidget) {
        return chatWidget.containsText("Off");
    }

    private static int getChatActionsWidgetId(int chatWidgetId) {
        return chatWidgetId - 3;
    }
}