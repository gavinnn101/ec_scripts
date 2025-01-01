package com.gavin101.amethystaio.branches.CommonBranches;

import com.gavin101.amethystaio.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;

public class CacheItemsBranch extends Branch {
    @Override
    public boolean isValid() {
        return !Main.itemsCached
                && !Players.localPlayer().isMoving()
                && !Players.localPlayer().isAnimating();
    }
}
