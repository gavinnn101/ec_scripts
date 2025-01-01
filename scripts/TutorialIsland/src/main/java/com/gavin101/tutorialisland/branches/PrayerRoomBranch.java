package com.gavin101.tutorialisland.branches;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;

public class PrayerRoomBranch extends Branch {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return tutorialProgress >= 540 && tutorialProgress < 620
                && !Players.localPlayer().isAnimating()
                && !Players.localPlayer().isMoving();
    }
}
