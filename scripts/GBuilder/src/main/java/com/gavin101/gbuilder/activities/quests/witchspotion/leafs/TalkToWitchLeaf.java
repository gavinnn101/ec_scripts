package com.gavin101.gbuilder.activities.quests.witchspotion.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.activities.quests.witchspotion.constants.Constants;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.data.NpcID;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToWitchLeaf extends Leaf {
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
        GLib.talkWithNpc(NpcID.HETTY, Constants.WITCHS_POTION_AREA, chatOptions);
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getNormal();
    }
}
