package com.gavin101.gbuilder.activities.quests.impcatcher.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.RectArea;

public class TalkToWizardLeaf extends Leaf {
    private static final String[] CHAT_OPTIONS = {
            "Give me a quest please.",
            "Yes."
    };
    private static final RectArea WIZARD_AREA = new RectArea(3103, 3163, 3105, 3161, 2);


    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        GLib.talkWithNpc("Wizard Mizgog", WIZARD_AREA, CHAT_OPTIONS);
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getNormal();
    }
}
