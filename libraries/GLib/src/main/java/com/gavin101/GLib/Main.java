package com.gavin101.GLib;

import net.eternalclient.api.accessors.AttackStyle;
import net.eternalclient.api.accessors.Combat;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.events.AbstractEvent;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.widgets.WidgetChild;

public class Main {
    public class SetAttackStyleEvent extends AbstractEvent {

        private final AttackStyle attackStyle;
        private static final int ATTACK_STYLE_WIDGET_ID = 593;
        private static final int ACCURATE_CHILD_WIDGET_ID = 5;
        private static final int AGGRESSIVE_CHILD_WIDGET_ID = 9;
        private static final int CONTROLLED_CHILD_WIDGET_ID = 13;
        private static final int DEFENSIVE_CHILD_WIDGET_ID = 17;

        public SetAttackStyleEvent(AttackStyle attackStyle) {
            this.attackStyle = attackStyle;
        }

        public boolean setAttackStyle(AttackStyle attackStyle) {
            WidgetChild widget;

            switch (attackStyle) {
                default:
                case ACCURATE:
                    widget = Widgets.getWidgetChild(ATTACK_STYLE_WIDGET_ID, ACCURATE_CHILD_WIDGET_ID);
                    break;
                case AGGRESSIVE:
                    widget = Widgets.getWidgetChild(ATTACK_STYLE_WIDGET_ID, AGGRESSIVE_CHILD_WIDGET_ID);
                    break;
                case CONTROLLED:
                    widget = Widgets.getWidgetChild(ATTACK_STYLE_WIDGET_ID, CONTROLLED_CHILD_WIDGET_ID);
                    break;
                case DEFENSIVE:
                    widget = Widgets.getWidgetChild(ATTACK_STYLE_WIDGET_ID, DEFENSIVE_CHILD_WIDGET_ID);
                    break;
            }

            return widget != null && (new WidgetEvent(widget).setEventCompleteCondition(() -> Combat.getAttackStyle().equals(attackStyle))).executed();
        }

        @Override
        public int onLoop() {
            if (setAttackStyle(attackStyle)) {
                MethodProvider.sleep(ReactionGenerator.getNormal());
                if (attackStyle == Combat.getAttackStyle()) {
                    setCompleted("Successfully set " + attackStyle + " attack style.");
                } else {
                    setFailed("Failed to set " + attackStyle + " attack style.");
                }
                return 0;
            }
            return 0;
        }
    }
}