package com.gavin101.tutorialisland.branches;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;

public class SurvivalExpertBranch extends Branch {
    @Override
    public boolean isValid() {
        int tutorailProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return tutorailProgress >= 20 && tutorailProgress < 130
                && !Players.localPlayer().isAnimating()
                && !Players.localPlayer().isMoving();
    }
}
