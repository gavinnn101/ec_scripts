package com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.ImpCatcherLeafs;

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

public class TalkToWizardLeaf extends Leaf {

    private static final String[] chatOptions = {
            "Give me a quest please.",
            "Yes."
    };
    @Override
    public boolean isValid() {
        return !GActivityHelper.needToBuyItems() && GLib.inventoryContainsAll(Constants.IMP_CATCHER_QUEST_ITEMS);
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to Wizard Mizgog.");
        NPC wizard = NPCs.closest("Wizard Mizgog");
        if (wizard != null && wizard.canReach()) {
            GLib.talkWithNpc("Wizard Mizgog", chatOptions);
        } else {
            Log.debug("Walking to Imp Catcher area.");
            Walking.walk(Constants.IMP_CATCHER_TILE,
                    () -> Players.localPlayer().getWorldTile().equals(Constants.IMP_CATCHER_TILE)
            );
        }
        return ReactionGenerator.getNormal();
    }
}
