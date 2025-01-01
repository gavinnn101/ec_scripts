package com.gavin101.tutorialisland.leafs.BankRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;

public class GoToAccountGuideLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 525;
    }

    @Override
    public int onLoop() {
        Log.info("Going to account guide.");
        NPC guide = NPCs.closest("Account Guide");
        if (guide != null) {
            Walking.walk(guide.getWorldTile(), guide::canReach);
        }
        return ReactionGenerator.getNormal();
    }
}
