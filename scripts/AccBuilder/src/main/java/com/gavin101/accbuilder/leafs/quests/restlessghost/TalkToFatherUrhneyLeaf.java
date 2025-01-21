package com.gavin101.accbuilder.leafs.quests.restlessghost;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.quests.RestlessGhost;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToFatherUrhneyLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Quest.THE_RESTLESS_GHOST.getState() == 1;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Father Urhney",
                RestlessGhost.FATHER_URHNEY_AREA,
                RestlessGhost.FATHER_URHNEY_CHAT_OPTIONS
        );
        return ReactionGenerator.getPredictable();
    }
}
