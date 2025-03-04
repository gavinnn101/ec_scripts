package com.gavin101.tutorialisland.leafs.MinerRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.data.ObjectID;
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
            mineRock(ObjectID.TIN_ROCKS);
        } else if (tutorialProgress == 310) {
            mineRock(ObjectID.COPPER_ROCKS);
        }
        return ReactionGenerator.getNormal();
    }

    public void mineRock(Integer rockId) {
        Log.info("Mining rock with id: " +rockId);
        GameObject rock = GameObjects.closest(rockId);
        if (rock != null && rock.canReach()) {
            Log.debug("Selecting 'Mine' on " +rock.getName());
            new EntityInteractEvent(rock, "Mine").setEventCompleteCondition(
                    () -> Players.localPlayer().isAnimating(), Calculations.random(2500, 5000)
            ).execute();
        }
    }
}
