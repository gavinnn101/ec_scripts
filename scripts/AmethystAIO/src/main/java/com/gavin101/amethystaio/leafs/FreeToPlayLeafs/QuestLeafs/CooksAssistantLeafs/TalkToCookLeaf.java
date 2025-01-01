package com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.CooksAssistantLeafs;

import com.gavin101.amethystaio.Constants;
import com.gavin101.amethystaio.GLib;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;

public class TalkToCookLeaf extends Leaf {

    private static final String[] chatOptions = {
            "What's wrong?",
            "Yes",
    };
    @Override
    public boolean isValid() {
        return GLib.inventoryContainsAll(Constants.COOKS_ASSISTANT_QUEST_ITEMS);
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to the cook for cooks assistant quest.");
        NPC cook = NPCs.closest("Cook");
        if (cook != null && cook.canReach()) {
            GLib.talkWithNpc("Cook", chatOptions);
        } else {
            Log.debug("Walking to cook.");
            Walking.walk(Constants.COOKS_ASSISTANT_AREA.getRandomTile(),
                    () -> Constants.COOKS_ASSISTANT_AREA.contains(Players.localPlayer())
            );
        }
        return ReactionGenerator.getNormal();
    }
}
