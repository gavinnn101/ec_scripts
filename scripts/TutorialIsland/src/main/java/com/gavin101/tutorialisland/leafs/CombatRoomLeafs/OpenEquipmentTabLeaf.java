package com.gavin101.tutorialisland.leafs.CombatRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import com.gavin101.tutorialisland.GLib;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.tabs.Tab;

public class OpenEquipmentTabLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 390;
    }

    @Override
    public int onLoop() {
        GLib.openTab(Tab.EQUIPMENT);
        return ReactionGenerator.getLowPredictable();
    }
}
