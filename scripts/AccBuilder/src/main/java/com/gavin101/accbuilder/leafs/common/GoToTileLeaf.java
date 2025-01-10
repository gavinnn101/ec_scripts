package com.gavin101.accbuilder.leafs.common;

import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.walking.Walking;

@RequiredArgsConstructor
public class GoToTileLeaf extends Leaf {
    private final WorldTile tile;

    @Override
    public boolean isValid() {
        return !Players.localPlayer().getWorldTile().equals(tile);
    }

    @Override
    public int onLoop() {
        Log.info("Walking to tile: " +tile.toString());
        Walking.walk(tile);
        Log.info("Walk to tile finished");
        return ReactionGenerator.getNormal();
    }
}
