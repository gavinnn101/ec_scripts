package com.gavin101.test.leafs;

import com.gavin101.GLib.GLib;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.widgets.WidgetChild;

public class TestLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        Log.info("Our test leaf is being executed.");
        WidgetChild setNameWidget = getSetDisplayNameChildWidget(19);
        while (true) {
            Log.info("Is set name button valid?: " + GLib.isWidgetValid(setNameWidget));
            MethodProvider.tickSleep(2);
        }
//        return ReactionGenerator.getNormal();
    }

    private WidgetChild getSetDisplayNameChildWidget(int childId) {
        return Widgets.getWidgetChild(558, childId);
    }
}
