package com.gavin101.accbuilder.leafs.quests.sheepshearer;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.quests.SheepShearer;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TalkToFarmerLeaf extends Leaf {
    private static final String farmerName = "Fred the Farmer";
    private static final String[] chatOptions = {
            "I'm looking for a quest.",
            "Yes."
    };
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to: " +farmerName);
        GLib.talkWithNpc(farmerName, SheepShearer.SHEEP_SHEARER_AREA, chatOptions);
        return ReactionGenerator.getNormal();
    }
}
