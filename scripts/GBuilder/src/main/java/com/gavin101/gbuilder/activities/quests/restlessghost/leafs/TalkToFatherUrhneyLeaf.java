package com.gavin101.gbuilder.activities.quests.restlessghost.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.data.NpcID;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToFatherUrhneyLeaf extends Leaf {
    private static final RectArea FATHER_URHNEY_AREA = new RectArea(3151, 3173, 3144, 3177);
    private static final String[] FATHER_URHNEY_CHAT_OPTIONS = {
            "Father Aereck sent me to talk to you.",
            "He's got a ghost haunting his graveyard."
    };

    @Override
    public boolean isValid() {
        return Quest.THE_RESTLESS_GHOST.getState() == 1;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc(NpcID.FATHER_URHNEY, FATHER_URHNEY_AREA, FATHER_URHNEY_CHAT_OPTIONS);
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getNormal();
    }
}
