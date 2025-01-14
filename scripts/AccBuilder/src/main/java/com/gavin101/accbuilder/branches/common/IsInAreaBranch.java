package com.gavin101.accbuilder.branches.common;

import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.map.Area;

@RequiredArgsConstructor
public class IsInAreaBranch extends Branch {
    private final Area area;

    @Override
    public boolean isValid() {
        return area.contains(Players.localPlayer());
    }
}
