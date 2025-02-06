package com.gavin101.gbuilder.utility.leafs;

import com.gavin101.GLib.GLib;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;

public class EndScriptLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int totalLevel = Skills.getTotalLevel();
        return totalLevel >= 500 && !GLib.isTradeRestricted();
    }

    @Override
    public int onLoop() {
        GLib.endScript(true, "Build finished, ending script.");
        MethodProvider.sleep(Calculations.random(10_000, 20_000));
        return ReactionGenerator.getPredictable();
    }
}
