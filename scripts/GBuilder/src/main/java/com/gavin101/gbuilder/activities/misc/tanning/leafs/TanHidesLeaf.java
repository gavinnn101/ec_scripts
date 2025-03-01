package com.gavin101.gbuilder.activities.misc.tanning.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.activities.misc.tanning.Tanning;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.data.MenuAction;
import net.eternalclient.api.data.MenuOpcode;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.widgets.WidgetChild;

@RequiredArgsConstructor
public class TanHidesLeaf extends Leaf {
    private final Tanning.LeatherType leatherType;

    private static final int tannerWindowWidgetId = 324;

    @Override
    public boolean isValid() {
        return Inventory.contains(ItemID.COINS_995, leatherType.getRawMaterialId());
    }

    @Override
    public int onLoop() {
        Log.info("Tanning material: " + GLib.getItemName(leatherType.getRawMaterialId()));
        if (isTannerWindowOpen()) {
            Log.debug("Tanner window is open");
            String leatherName = leatherType.getName();
            Log.debug("Looking for tanAll widget for leather: " +leatherName);
            WidgetChild tanAllWidget = getTanAllWidget(leatherName);
            if (tanAllWidget != null && tanAllWidget.isVisible()) {
                Log.debug("Found tanAll widget");
                new WidgetEvent(tanAllWidget, new MenuAction()
                        .setOpcode(MenuOpcode.WIDGET_TYPE_1)
                        .setAction(tanAllWidget.getTooltip()))
                        .setEventCompleteCondition(
                                () -> !Inventory.contains(ItemID.COWHIDE), Calculations.random(2000, 3000)
                        ).execute();
            }
        } else {
            NPC ellis = NPCs.closest("Ellis");
            Log.debug("Looking for Ellis");
            if (ellis != null && ellis.canReach()) {
                Log.debug("Trading Ellis");
                new EntityInteractEvent(ellis, "Trade").setEventCompleteCondition(
                        TanHidesLeaf::isTannerWindowOpen, Calculations.random(1500, 3000)
                ).execute();
            }
        }
        return ReactionGenerator.getNormal();
    }

    private static boolean isTannerWindowOpen() {
        return Widgets.getWidget(tannerWindowWidgetId).isVisible();
    }

    private static WidgetChild getTanAllWidget(String leatherName) {
        switch (leatherName) {
            case "Leather":
                return Widgets.getWidgetChild(tannerWindowWidgetId, 124);
            case "Hard leather":
                return Widgets.getWidgetChild(tannerWindowWidgetId, 125);
            case "Snakeskin":
                return Widgets.getWidgetChild(tannerWindowWidgetId, 126);
            case "Green d'hide":
                return Widgets.getWidgetChild(tannerWindowWidgetId, 128);
            case "Blue d'hide":
                return Widgets.getWidgetChild(tannerWindowWidgetId, 129);
            case "Red d'hide":
                return Widgets.getWidgetChild(tannerWindowWidgetId, 130);
            case "Black d'hide":
                return Widgets.getWidgetChild(tannerWindowWidgetId, 131);
        }
        return null;
    }
}
