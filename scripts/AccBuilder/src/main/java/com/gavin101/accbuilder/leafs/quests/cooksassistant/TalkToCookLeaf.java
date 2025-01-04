package com.gavin101.accbuilder.leafs.quests.cooksassistant;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.quests.CooksAssistant;
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
        GLib.talkWithNpc(cookName, CooksAssistant.COOKS_ASSISTANT_AREA, chatOptions);
        return ReactionGenerator.getNormal();
    }
}
