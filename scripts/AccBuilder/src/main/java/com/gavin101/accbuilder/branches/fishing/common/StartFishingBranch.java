package com.gavin101.accbuilder.branches.fishing.common;

import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.map.Area;

public class StartFishingBranch extends Branch {
    private final Area fishingArea;

    public StartFishingBranch(Area fishingArea) {
        this.fishingArea = fishingArea;
    }

    @Override
    public boolean isValid() {
        return fishingArea.contains(Players.localPlayer());
    }
}
