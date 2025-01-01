package com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.DoricsQuestLeafs;

import com.gavin101.amethystaio.Constants;
import com.gavin101.amethystaio.GLib;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;

public class TalkToDoricLeaf extends Leaf {

    private static final String[] chatOptions = {
            "I wanted to use your anvils.",
            "Yes",
            "Yes, I will get you the materials."
    };
    @Override
    public boolean isValid() {
        return GLib.inventoryContainsAll(Constants.DORICS_QUEST_ITEMS);
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to Doric");
        NPC doric = NPCs.closest("Doric");
        if (doric != null && doric.canReach()) {
            GLib.talkWithNpc("Doric", chatOptions);
        } else {
            Log.debug("Walking to Dorics house.");
            Walking.walk(Constants.DORICS_HOUSE_AREA.getRandomTile(),
                    () -> Constants.DORICS_HOUSE_AREA.contains(Players.localPlayer())
            );
        }
        return ReactionGenerator.getNormal();
    }
}
