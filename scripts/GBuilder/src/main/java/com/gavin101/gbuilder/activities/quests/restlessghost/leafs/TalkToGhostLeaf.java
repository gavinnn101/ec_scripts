package com.gavin101.gbuilder.activities.quests.restlessghost.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.activities.quests.restlessghost.Constants;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TalkToGhostLeaf extends Leaf {
    private static final String[] GHOST_CHAT_OPTIONS = {
            "Yep, now tell me what the problem is.",
            "Yep, clever aren't I?.",
            "Yes, ok. Do you know WHY you're a ghost?",
            "Yes, ok. Do you know why you're a ghost?"
    };
    @Override
    public boolean isValid() {
        return Constants.getRestlessGhost() != null;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Restless ghost", Constants.GRAVEYARD_COFFIN_AREA, GHOST_CHAT_OPTIONS);
        return ReactionGenerator.getPredictable();
    }
}
