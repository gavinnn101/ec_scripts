package com.gavin101.tutorialisland.leafs.MinerRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;

public class MineRocksLeaf extends Leaf {

    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return (tutorialProgress == 300 || tutorialProgress == 310);
    }

    @Override
    public int onLoop() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        if (tutorialProgress == 300) {
            mineRock("Tin rocks");
        } else if (tutorialProgress == 310) {
            mineRock("Copper rocks");
        }
        return ReactionGenerator.getAFK();
    }

    public void mineRock(String rockName) {
        Log.info("Mining: " +rockName);
        GameObject rock = GameObjects.closest(rockName);
        if (rock != null && rock.canReach()) {
            Log.debug("Selecting 'Mine' on " +rockName);
            new EntityInteractEvent(rock, "Mine").setEventCompleteCondition(
                    () -> Players.localPlayer().isAnimating(), Calculations.random(2500, 5000)
            ).execute();
        }
    }
}
