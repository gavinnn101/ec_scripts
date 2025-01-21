package com.gavin101.accbuilder.leafs.quests.restlessghost;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.quests.RestlessGhost;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TalkToGhostLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return RestlessGhost.getRestlessGhost() != null;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Restless ghost", RestlessGhost.GRAVEYARD_COFFIN_AREA, RestlessGhost.GHOST_CHAT_OPTIONS);
        return ReactionGenerator.getPredictable();
    }
}
