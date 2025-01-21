package com.gavin101.accbuilder.leafs.quests.runemysteries;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.quests.RuneMysteries;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToDukeLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Quest.RUNE_MYSTERIES.getState() == 0;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Duke Horacio", RuneMysteries.DUKE_AREA, RuneMysteries.DUKE_CHAT_OPTIONS);
        return ReactionGenerator.getPredictable();
    }
}
