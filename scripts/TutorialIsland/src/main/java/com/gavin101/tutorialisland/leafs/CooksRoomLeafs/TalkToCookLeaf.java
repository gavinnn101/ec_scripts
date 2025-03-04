package com.gavin101.tutorialisland.leafs.CooksRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.data.NpcID;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TalkToCookLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return tutorialProgress == 120 || tutorialProgress == 130 || tutorialProgress == 140;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc(NpcID.MASTER_CHEF, Constants.COOKS_AREA);
        return ReactionGenerator.getNormal();
    }
}
