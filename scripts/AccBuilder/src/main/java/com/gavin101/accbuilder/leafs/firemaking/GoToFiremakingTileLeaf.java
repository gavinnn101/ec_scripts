package com.gavin101.accbuilder.leafs.firemaking;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.constants.firemaking.Firemaking;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.walking.Walking;

import java.util.List;

@RequiredArgsConstructor
public class GoToFiremakingTileLeaf extends Leaf {
    private final List<WorldTile> firemakingTiles;

    @Override
    public boolean isValid() {
        WorldTile playerTile = Players.localPlayer().getWorldTile();
        return (Inventory.isFull() && !firemakingTiles.contains(playerTile)) || !Firemaking.atValidFiremakingTile();
    }

    @Override
    public int onLoop() {
        Log.info("Walking to firemaking tile.");
        Walking.walkExact(GLib.getRandomTile(firemakingTiles));
        return ReactionGenerator.getNormal();
    }
}
