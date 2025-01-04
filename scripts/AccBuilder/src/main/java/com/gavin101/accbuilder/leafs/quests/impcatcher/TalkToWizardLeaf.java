package com.gavin101.accbuilder.leafs.quests.impcatcher;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.quests.ImpCatcher;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TalkToWizardLeaf extends Leaf {
    private static final String wizardName = "Wizard Mizgog";
    private static final String[] chatOptions = {
            "Give me a quest please.",
            "Yes."
    };
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        Log.info("Trying to talk to: " +wizardName);
        GLib.talkWithNpc(wizardName, ImpCatcher.IMP_CATCHER_AREA, chatOptions);
        return ReactionGenerator.getNormal();
    }
}
