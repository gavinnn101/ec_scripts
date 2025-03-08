package com.gavin101.gbuilder.activities.quests.goblindiplomacy.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.map.RectArea;

public class TalkToGeneralLeaf extends Leaf {
    private static final String[] GENERALS = {"General Bentnoze", "General Wartface"};
    private static final String[] CHAT_OPTIONS = {
            "Do you want me to pick an armour colour for you?",
            "What about a different colour?",
            "Yes.",
            "Yes, he looks fat.",
            "I have some blue armour here.",
            "I have some brown armour here."
    };
    private static final RectArea GOBLIN_GENERALS_HUT_AREA = new RectArea(2961, 3513, 2956, 3510, 0);
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        int generalIndex = Calculations.random(0, 1);
        String general = GENERALS[generalIndex];
        GLib.talkWithNpc(general, GOBLIN_GENERALS_HUT_AREA, CHAT_OPTIONS);
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getNormal();
    }
}
