package com.gavin101.tutorialisland.leafs.BankRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.tabs.Tab;

public class OpenManagementTabLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 531;
    }

    @Override
    public int onLoop() {
        GLib.openTab(Tab.ACCOUNT);
        return ReactionGenerator.getPredictable();
    }
}
