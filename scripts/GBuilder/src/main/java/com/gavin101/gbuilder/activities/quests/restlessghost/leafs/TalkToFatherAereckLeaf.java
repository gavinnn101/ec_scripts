package com.gavin101.gbuilder.activities.quests.restlessghost.leafs;

import com.gavin101.GLib.GLib;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToFatherAereckLeaf extends Leaf {
    private static final RectArea LUMBRIDGE_CHURCH_AREA = new RectArea(3239, 3215, 3247, 3204);
    private static final String[] FATHER_AERECK_CHAT_OPTIONS = {
            "I'm looking for a quest!",
            "Ok, let me help then.",
            "Yes."
    };

    @Override
    public boolean isValid() {
        return Quest.THE_RESTLESS_GHOST.getState() == 0;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Father Aereck", LUMBRIDGE_CHURCH_AREA, FATHER_AERECK_CHAT_OPTIONS);
        return ReactionGenerator.getPredictable();
    }
}
