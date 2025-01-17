package com.gavin101.GLib.branches.common;

import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.interactives.Player;

public class IsAfkBranch extends Branch {
    @Override
    public boolean isValid() {
        Player localPlayer = Players.localPlayer();
        return !localPlayer.isMoving() && !localPlayer.isAnimating();
    }
}
