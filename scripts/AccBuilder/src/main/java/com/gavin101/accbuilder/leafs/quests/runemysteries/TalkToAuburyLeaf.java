package com.gavin101.accbuilder.leafs.quests.runemysteries;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.quests.RuneMysteries;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToAuburyLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Quest.RUNE_MYSTERIES.getState() == 3;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Aubury", RuneMysteries.AUBURY_AREA, RuneMysteries.AUBURY_CHAT_OPTIONS);
        return ReactionGenerator.getPredictable();
    }
}
