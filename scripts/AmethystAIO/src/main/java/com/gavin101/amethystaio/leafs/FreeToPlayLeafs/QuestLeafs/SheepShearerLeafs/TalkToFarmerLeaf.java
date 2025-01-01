package com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.SheepShearerLeafs;

import com.gavin101.amethystaio.Constants;
import com.gavin101.amethystaio.GActivityHelper;
import com.gavin101.amethystaio.GLib;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;

public class TalkToFarmerLeaf extends Leaf {

    private static final String[] chatOptions = {
            "I'm looking for a quest.",
            "Yes."
    };
    @Override
    public boolean isValid() {
        return !GActivityHelper.needToBuyItems() && GLib.inventoryContainsAll(Constants.SHEEP_SHEARER_QUEST_ITEMS);
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to Fred the Farmer.");
        NPC farmer = NPCs.closest("Fred the Farmer");
        if (farmer != null && farmer.canReach()) {
            GLib.talkWithNpc("Fred the Farmer", chatOptions);
        } else {
            Log.debug("Walking to Imp Catcher area.");
            Walking.walk(Constants.SHEEP_SHEARER_AREA.getRandomTile(),
                    () -> Constants.SHEEP_SHEARER_AREA.contains(Players.localPlayer())
            );
        }
        return ReactionGenerator.getNormal();
    }
}
