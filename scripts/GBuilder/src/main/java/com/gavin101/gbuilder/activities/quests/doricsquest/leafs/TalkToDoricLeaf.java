package com.gavin101.gbuilder.activities.quests.doricsquest.leafs;

import com.gavin101.GLib.GLib;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.RectArea;

public class TalkToDoricLeaf extends Leaf {
    private static final String DORIC_NAME = "Doric";
    private static final String[] CHAT_OPTIONS = {
            "I wanted to use your anvils.",
            "Yes",
            "Yes, I will get you the materials."
    };
    private static final RectArea DORICS_HOUSE_AREA = new RectArea(2949, 3452, 2953, 3449);

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to: " +DORIC_NAME);
        GLib.talkWithNpc(DORIC_NAME, DORICS_HOUSE_AREA, CHAT_OPTIONS);
        return ReactionGenerator.getNormal();
    }
}
