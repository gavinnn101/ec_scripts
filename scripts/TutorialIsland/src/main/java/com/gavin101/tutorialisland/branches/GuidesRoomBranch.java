package com.gavin101.tutorialisland.branches;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;



public class GuidesRoomBranch extends Branch
{
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) < 20
                && !Players.localPlayer().isAnimating()
                && !Players.localPlayer().isMoving();
    }
}