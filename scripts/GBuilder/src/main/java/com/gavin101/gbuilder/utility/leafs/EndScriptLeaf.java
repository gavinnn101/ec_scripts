package com.gavin101.gbuilder.utility.leafs;

import com.gavin101.GLib.GLib;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;

public class EndScriptLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int totalLevel = Skills.getTotalLevel();
        return totalLevel >= 500 && !GLib.isTradeRestricted();
    }

    @Override
    public int onLoop() {
        GLib.endScript(true, "Build finished, ending script.", true);
        return ReactionGenerator.getPredictable();
    }
}
