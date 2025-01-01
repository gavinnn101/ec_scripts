package com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.GoblinDiplomacyLeafs;

import com.gavin101.amethystaio.Constants;
import com.gavin101.amethystaio.GActivityHelper;
import com.gavin101.amethystaio.GLib;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;

public class TalkToGeneralLeaf extends Leaf {

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
        return !GActivityHelper.needToBuyItems() && !Inventory.contains(i -> i.containsName("dye"));
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to General Bentnoze.");
        NPC general = NPCs.closest("General Bentnoze");
        if (general != null && general.canReach()) {
            GLib.talkWithNpc("General Bentnoze", chatOptions);
        } else {
            Log.debug("Walking to goblin diplomacy area.");
            Walking.walk(Constants.GOBLIN_DIPLOMACY_AREA.getRandomTile(),
                    () -> Constants.GOBLIN_DIPLOMACY_AREA.contains(Players.localPlayer())
            );
        }
        return ReactionGenerator.getNormal();
    }
}
