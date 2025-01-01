package com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.WitchsPotionLeafs;

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
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.walking.Walking;

public class TalkToHettyLeaf extends Leaf {

    private static final String[] chatOptions = {
            "I am in search of a quest.",
            "Yes."
    };
    @Override
    public boolean isValid() {
        return !GActivityHelper.needToBuyItems()
                && (Quest.WITCHS_POTION.isNotStarted() && GLib.inventoryContainsAll(Constants.WITCHS_POTION_QUEST_ITEMS))
                || Inventory.containsAll("Burnt meat", "Rat's tail");
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to Hetty.");
        NPC hetty = NPCs.closest("Hetty");
        if (hetty != null && hetty.canReach()) {
            GLib.talkWithNpc("Hetty", chatOptions);
        } else {
            Log.debug("Walking to Witchs Potion area.");
            Walking.walk(Constants.WITCHS_POTION_AREA.getRandomTile(),
                    () -> Constants.WITCHS_POTION_AREA.contains(Players.localPlayer())
            );
        }
        return ReactionGenerator.getNormal();
    }
}
