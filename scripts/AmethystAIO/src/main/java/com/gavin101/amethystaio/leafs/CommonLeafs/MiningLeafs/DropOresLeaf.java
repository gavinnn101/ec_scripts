package com.gavin101.amethystaio.leafs.CommonLeafs.MiningLeafs;

import com.gavin101.amethystaio.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;

public class DropOresLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Inventory.isFull()
                && Players.localPlayer().getWorldTile().equals(Main.miningSpot);
    }

    @Override
    public int onLoop() {
        Log.info("Dropping items from inventory.");
        if (Inventory.dropAll(i -> !i.containsName("pickaxe"))) {
            MethodProvider.sleepUntil(() -> Inventory.onlyContains(
                    i -> i.containsName("pickaxe")), Calculations.random(3000, 6000)
            );
        }
        return ReactionGenerator.getNormal();
    }
}
