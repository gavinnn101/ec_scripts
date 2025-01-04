package com.gavin101.accbuilder.leafs.quests.goblindiplomacy;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.quests.GoblinDiplomacy;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TalkToGeneralLeaf extends Leaf {
    private static final String generalName = "General Bentnoze";
    private static final String[] chatOptions = {
            "Do you want me to pick an armour colour for you?",
            "What about a different colour?",
            "Yes.",
            "Yes, he looks fat.",
            "I have some blue armour here.",
            "I have some brown armour here."
    };
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to: " +generalName);
        GLib.talkWithNpc(generalName, GoblinDiplomacy.GOBLIN_DIPLOMACY_AREA, chatOptions);
        return ReactionGenerator.getNormal();
    }
}
