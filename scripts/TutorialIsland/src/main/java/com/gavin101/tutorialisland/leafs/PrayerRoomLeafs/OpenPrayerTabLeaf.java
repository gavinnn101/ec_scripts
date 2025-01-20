package com.gavin101.tutorialisland.leafs.PrayerRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.tabs.Tab;

public class OpenPrayerTabLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 560;
    }

    @Override
    public int onLoop() {
        GLib.openTab(Tab.PRAYER);
        return ReactionGenerator.getPredictable();
    }
}
