package com.gavin101.tutorialisland.leafs.MageRoomLeafs;

import com.gavin101.GLib.GLib;
import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;

public class TalkToMageLeaf extends Leaf {

    private final String[] chatOptions = {
            "Yes.",
            "No, I'm not planning to do that.",
            "Yes, send me to the mainland"
    };

    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return (tutorialProgress == 610 || tutorialProgress == 620 || tutorialProgress == 640 || tutorialProgress == 670);
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Magic Instructor", Constants.MAGE_ROOM_AREA, chatOptions);
        return ReactionGenerator.getPredictable();
    }
}
