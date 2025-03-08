package com.gavin101.gbuilder.activities.quests.cooksassistant.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.RectArea;

public class TalkToCookLeaf extends Leaf {
    private static final String[] CHAT_OPTIONS = {
            "What's wrong?",
            "Yes",
    };
    private static final RectArea COOK_KITCHEN_AREA = new RectArea(3209, 3216, 3211, 3213);

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Cook", COOK_KITCHEN_AREA, CHAT_OPTIONS);
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getNormal();
    }
}
