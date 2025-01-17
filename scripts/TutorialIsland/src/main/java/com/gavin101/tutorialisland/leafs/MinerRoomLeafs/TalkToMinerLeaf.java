package com.gavin101.tutorialisland.leafs.MinerRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;

public class TalkToMinerLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return (tutorialProgress == 250 || tutorialProgress == 260 || tutorialProgress == 330);
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Mining Instructor", Constants.MINING_INSTRUCTOR_AREA);
        return ReactionGenerator.getNormal();
    }
}
