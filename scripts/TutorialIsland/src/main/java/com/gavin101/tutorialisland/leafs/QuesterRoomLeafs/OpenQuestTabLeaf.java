package com.gavin101.tutorialisland.leafs.QuesterRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.Dialogues;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.tabs.Tab;
import net.eternalclient.api.wrappers.tabs.Tabs;

public class OpenQuestTabLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 230
                && !Tabs.isDisabled(Tab.QUEST)
                && !Tab.QUEST.isOpen();
    }

    @Override
    public int onLoop() {
        GLib.openTab(Tab.QUEST);
        return ReactionGenerator.getNormal();
    }
}
