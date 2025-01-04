package com.gavin101.accbuilder.leafs.quests.witchspotion;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.quests.WitchsPotion;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToWitchLeaf extends Leaf {
    private static final String witchName = "Hetty";
    private static final String[] chatOptions = {
            "I am in search of a quest.",
            "Yes."
    };
    @Override
    public boolean isValid() {
        return Quest.WITCHS_POTION.isNotStarted() || (Inventory.containsAll(ItemID.BURNT_MEAT, ItemID.RATS_TAIL));
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to: " +witchName);
        GLib.talkWithNpc(witchName, WitchsPotion.WITCHS_POTION_AREA, chatOptions);
        return ReactionGenerator.getNormal();
    }
}
