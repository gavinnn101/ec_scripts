package com.gavin101.gbuilder.activities.quests.runemysteries.leafs;

import com.gavin101.GLib.GLib;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToSedridorLeaf extends Leaf {
    private static final RectArea SEDRIDOR_AREA = new RectArea(3096, 9574, 3107, 9566);
    private static final String[] SEDRIDOR_CHAT_OPTIONS = {
            "I'm looking for the head wizard.",
            "Okay, here you are.",
            "Go ahead.",
            "Yes, certainly."
    };

    @Override
    public boolean isValid() {
        int questState = Quest.RUNE_MYSTERIES.getState();
        return questState == 1 || questState == 2 || questState == 5;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Archmage Sedridor", SEDRIDOR_AREA, SEDRIDOR_CHAT_OPTIONS);
        return ReactionGenerator.getPredictable();
    }
}
