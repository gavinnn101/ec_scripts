package com.gavin101.tutorialisland.branches;

import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;

public class BankRoomBranch extends Branch {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(281);
        return tutorialProgress >= 510 && tutorialProgress < 540
                && !Players.localPlayer().isMoving()
                && !Players.localPlayer().isAnimating();
    }
}
