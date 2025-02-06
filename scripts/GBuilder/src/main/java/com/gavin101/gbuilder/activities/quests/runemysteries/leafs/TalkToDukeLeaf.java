package com.gavin101.gbuilder.activities.quests.runemysteries.leafs;

import com.gavin101.GLib.GLib;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToDukeLeaf extends Leaf {
    private static final RectArea DUKE_AREA = new RectArea(3213, 3218, 3208, 3225, 1);
    private static final String[] DUKE_CHAT_OPTIONS = {
            "Have you any quests for me?",
            "Yes."
    };

    @Override
    public boolean isValid() {
        return Quest.RUNE_MYSTERIES.getState() == 0;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Duke Horacio", DUKE_AREA, DUKE_CHAT_OPTIONS);
        return ReactionGenerator.getPredictable();
    }
}
