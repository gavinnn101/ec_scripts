package com.gavin101.tutorialisland.leafs.MageRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import com.gavin101.tutorialisland.GLib;
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
        return (tutorialProgress == 620 || tutorialProgress == 640 || tutorialProgress == 670);
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to the magic instructor.");
        NPC mage = NPCs.closest("Magic Instructor");
        if (mage != null && mage.canReach()) {
            GLib.talkWithNpc("Magic Instructor", chatOptions);
        } else {
            Log.debug("Going to mage room");
            if (!Constants.MAGE_ROOM_AREA.contains(Players.localPlayer())) {
                Walking.walk(Constants.MAGE_ROOM_AREA.getRandomTile(),
                        () -> NPCs.closest("Magic Instructor") != null
                );
            }
        }
        return ReactionGenerator.getPredictable();
    }
}
