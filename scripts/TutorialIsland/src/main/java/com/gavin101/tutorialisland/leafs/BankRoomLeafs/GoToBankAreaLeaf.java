package com.gavin101.tutorialisland.leafs.BankRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.walking.Walking;

public class GoToBankAreaLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return tutorialProgress == 500;
    }

    @Override
    public int onLoop() {
        Log.info("Walking to the bank area.");
        Walking.walk(Constants.BANK_AREA.getRandomTile(), () -> Constants.BANK_AREA.contains(Players.localPlayer()));
        return ReactionGenerator.getPredictable();
    }
}
