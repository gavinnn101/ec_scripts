package com.gavin101.accbuilder.leafs.quests.runemysteries;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.quests.RuneMysteries;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToSedridorLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int questState = Quest.RUNE_MYSTERIES.getState();
        return questState == 1 || questState == 2 || questState == 5;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Archmage Sedridor", RuneMysteries.SEDRIDOR_AREA, RuneMysteries.SEDRIDOR_CHAT_OPTIONS);
        return ReactionGenerator.getPredictable();
    }
}
