package com.gavin101.tutorialisland.leafs.BankRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.data.NpcID;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TalkToAccountGuideLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return (tutorialProgress == 525 || tutorialProgress == 530 || tutorialProgress == 532);
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc(NpcID.ACCOUNT_GUIDE, Constants.ACCOUNT_GUIDE_AREA);
        return ReactionGenerator.getNormal();
    }
}
