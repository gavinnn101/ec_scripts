package com.gavin101.tutorialisland.leafs.SurvivalExpertLeafs;

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

public class ChopTreeLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 70;
    }

    @Override
    public int onLoop() {
        Log.info("Chopping tree");
        GameObject tree = GameObjects.closest("Tree");
        if (tree != null && tree.canReach()) {
            Log.debug("Interacting with tree 'Chop down'.");
            new EntityInteractEvent(tree, "Chop down").setEventCompleteCondition(
                    () -> Players.localPlayer().getAnimation() == Constants.CHOP_TREE_ANIMATION,
                    Calculations.random(2500, 5000)
            ).execute();
        }
        return ReactionGenerator.getAFK();
    }
}
