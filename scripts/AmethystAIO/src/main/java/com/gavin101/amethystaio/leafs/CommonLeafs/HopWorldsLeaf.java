package com.gavin101.amethystaio.leafs.CommonLeafs;

import com.gavin101.amethystaio.Main;
import net.eternalclient.api.accessors.Worlds;
import net.eternalclient.api.events.worldhopper.WorldHopperEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class HopWorldsLeaf extends Leaf {

    private final boolean hopToMembers;

    public HopWorldsLeaf(boolean hopToMembers) {
        this.hopToMembers = hopToMembers;
    }

    @Override
    public boolean isValid() {
        return Main.needToHopWorlds;
    }

    @Override
    public int onLoop() {
        Log.info("Hopping to a new world.");
        if (new WorldHopperEvent(Worlds.getRandomWorld(i -> i.isNormalized() && i.isMember() == hopToMembers)).executed()) {
            Main.needToHopWorlds = false;
        }
        return ReactionGenerator.getNormal();
    }
}
