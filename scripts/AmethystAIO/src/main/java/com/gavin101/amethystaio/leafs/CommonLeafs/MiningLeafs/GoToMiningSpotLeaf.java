package com.gavin101.amethystaio.leafs.CommonLeafs.MiningLeafs;

import com.gavin101.amethystaio.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.walking.Walking;

public class GoToMiningSpotLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return !Players.localPlayer().getWorldTile().equals(Main.miningSpot)
                && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        Log.info("Walking to mining spot tile.");
        Walking.walk(Main.miningSpot,
                () -> Players.localPlayer().getWorldTile().equals(Main.miningSpot)
        );
        return ReactionGenerator.getNormal();
    }
}