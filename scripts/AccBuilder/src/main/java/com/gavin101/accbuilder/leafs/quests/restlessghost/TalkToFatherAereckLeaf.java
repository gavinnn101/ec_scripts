package com.gavin101.accbuilder.leafs.quests.restlessghost;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.quests.RestlessGhost;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToFatherAereckLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Quest.THE_RESTLESS_GHOST.getState() == 0;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Father Aereck",
                RestlessGhost.LUMBRIDGE_CHURCH_AREA,
                RestlessGhost.FATHER_AERECK_CHAT_OPTIONS
        );
        return ReactionGenerator.getPredictable();
    }
}
