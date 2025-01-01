package com.gavin101.tutorialisland.branches;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;

public class MageRoomBranch extends Branch {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return tutorialProgress >= 620 && tutorialProgress < 1000
                && !Players.localPlayer().isMoving()
                && !Players.localPlayer().isAnimating();
    }
}
