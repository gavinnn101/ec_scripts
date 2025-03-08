package com.gavin101.gbuilder.utility.leafs;

import com.gavin101.gbuilder.Main;
import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.Worlds;
import net.eternalclient.api.events.worldhopper.WorldHopperEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.world.World;

public class HopWorldsLeaf extends Leaf {
    private static boolean hopToMembers;

    @Override
    public boolean isValid() {
        return Main.needToHopWorlds;
    }

    @Override
    public int onLoop() {
        Log.info("Hopping to a new world.");
        hopToMembers = Client.hasMembership();
        World world = Worlds.getRandomWorld(i -> i.isNormalized() && i.isMember() == hopToMembers);
        if (world != null) {
            if (new WorldHopperEvent(world).setEventCompleteCondition(
                    () -> Worlds.getCurrentWorld() == world.getId(), Calculations.random(5_000, 10_000)
            ).executed()) {
                Log.info("World hop successful.");
                Main.needToHopWorlds = false;
            }
        }
        return ReactionGenerator.getNormal();
    }
}
