package com.gavin101.gbuilder.activities.quests.sheepshearer.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.data.NpcID;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.RectArea;

public class TalkToFarmerLeaf extends Leaf {
    private static final String[] CHAT_OPTIONS = {
            "I'm looking for a quest.",
            "Yes."
    };
    private static final RectArea FARMER_AREA = new RectArea(3184, 3279, 3191, 3270, 0);

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc(NpcID.FRED_THE_FARMER, FARMER_AREA, CHAT_OPTIONS);
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getNormal();
    }
}
