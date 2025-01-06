package com.gavin101.test.accbuilder.quests.doricsquest;

import com.gavin101.GLib.GLib;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TalkToDoricLeaf extends Leaf {
    private static final String doricName = "Doric";
    private static final String[] chatOptions = {
            "I wanted to use your anvils.",
            "Yes",
            "Yes, I will get you the materials."
    };
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to: " +doricName);
        GLib.talkWithNpc(doricName, Constants.DORICS_QUEST_AREA, chatOptions);
        return ReactionGenerator.getNormal();
    }
}
