package com.gavin101.tutorialisland.leafs.SurvivalExpertLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.tabs.Tab;
import net.eternalclient.api.wrappers.tabs.Tabs;

public class OpenSkillsTabLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 50
                && !Tabs.isDisabled(Tab.SKILL)
                && !Tab.SKILL.isOpen();
    }

    @Override
    public int onLoop() {
        GLib.openTab(Tab.SKILL);
        return ReactionGenerator.getPredictable();
    }
}
