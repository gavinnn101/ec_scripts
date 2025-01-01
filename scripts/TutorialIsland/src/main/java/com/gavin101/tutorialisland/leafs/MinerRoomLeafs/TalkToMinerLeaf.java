package com.gavin101.tutorialisland.leafs.MinerRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import com.gavin101.tutorialisland.GLib;
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
        return (tutorialProgress == 260 || tutorialProgress == 330);
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to the mining instructor.");
        NPC miner = NPCs.closest("Mining Instructor");
        if (miner != null && miner.canReach()) {
            GLib.talkWithNpc("Mining Instructor");
        } else {
            Log.debug("Going to mining instructor area to talk to miner.");
            Walking.walk(Constants.MINING_INSTRUCTOR_AREA.getRandomTile(),
                    () -> NPCs.closest("Mining Instructor") != null
            );
        }
        return ReactionGenerator.getPredictable();
    }
}
