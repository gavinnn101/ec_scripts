package com.gavin101.accbuilder.branches.common;

import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.map.WorldTile;

@RequiredArgsConstructor
public class IsAtWorldTileBranch extends Branch {
    private final WorldTile tile;

    @Override
    public boolean isValid() {
        return Players.localPlayer().getWorldTile().equals(tile);
    }
}
