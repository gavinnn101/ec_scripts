package com.gavin101.gbuilder.activities.quests.runemysteries.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.PolyArea;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToAuburyLeaf extends Leaf {
    private static final PolyArea AUBURY_AREA = new PolyArea(
            new WorldTile(3252, 3403, 0),
            new WorldTile(3249, 3402, 0),
            new WorldTile(3252, 3399, 0),
            new WorldTile(3254, 3399, 0),
            new WorldTile(3256, 3401, 0),
            new WorldTile(3256, 3403, 0),
            new WorldTile(3254, 3404, 0)
    );
    private static final String[] AUBURY_CHAT_OPTIONS = {
            "I've been sent here with a package for you."
    };

    @Override
    public boolean isValid() {
        return Quest.RUNE_MYSTERIES.getState() == 3;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Aubury", AUBURY_AREA, AUBURY_CHAT_OPTIONS);
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getNormal();
    }
}
