package com.gavin101.gbuilder.activities.quests.cooksassistant.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.activities.quests.cooksassistant.CooksAssistant;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TalkToCookLeaf extends Leaf {
    private static final String cookName = "Cook";
    private static final String[] chatOptions = {
            "What's wrong?",
            "Yes",
    };

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to: " +cookName);
        GLib.talkWithNpc(cookName, CooksAssistant.COOK_KITCHEN_AREA, chatOptions);
        return ReactionGenerator.getNormal();
    }
}
