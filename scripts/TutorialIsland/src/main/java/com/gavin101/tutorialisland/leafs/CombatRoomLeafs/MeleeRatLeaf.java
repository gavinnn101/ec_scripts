package com.gavin101.tutorialisland.leafs.CombatRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;

public class MeleeRatLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return tutorialProgress == 450 || tutorialProgress == 460 && !Players.localPlayer().isInCombat();
    }

    @Override
    public int onLoop() {
        Log.info("Fighting a rat with melee.");
        NPC rat = NPCs.closest(g -> g.hasName("Giant rat")
                && g.canReach()
                && !g.isInCombat()
        );
        if (rat != null) {
            Log.debug("Interacting with rat 'Attack'.");
            new EntityInteractEvent(rat, "Attack").setEventCompleteCondition(
                    () -> Players.localPlayer().isInCombat(), Calculations.random(1500, 3500)
            ).execute();
        }
        return ReactionGenerator.getPredictable();
    }
}
