package com.gavin101.tutorialisland.leafs.CombatRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.equipment.Equipment;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;

public class FightRatLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return ((tutorialProgress == 450 || tutorialProgress == 460)
                || ((tutorialProgress == 480 || tutorialProgress == 490) && Equipment.contains("Shortbow", "Bronze arrow")))
                && !Players.localPlayer().isInCombat();
    }

    @Override
    public int onLoop() {
        Log.info("Fighting a rat.");
        NPC rat = NPCs.closest(g -> g.hasName("Giant rat") && !g.isInCombat()
                && (g.canReach() || Players.localPlayer().hasLineOfSightTo(g))
        );
        if (rat != null) {
            Log.debug("Interacting with rat 'Attack'.");
            new EntityInteractEvent(rat, "Attack").setEventCompleteCondition(
                    () -> Players.localPlayer().isInCombat(), Calculations.random(1500, 3500)
            ).execute();
        }
        // Sometimes we get stuck behind objects trying to range the rat even though we *should* have LOS of it.
        // So we'll move to a random tile in the area and try again.
        if (!Players.localPlayer().isInCombat()) {
            Log.debug("Didn't get in combat with the rat. Moving to a different tile.");
            Walking.localWalk(Constants.COMBAT_INSTRUCTOR_AREA.getRandomTile(),
                    () -> Constants.COMBAT_INSTRUCTOR_AREA.contains(Players.localPlayer())
            );
        }
        return ReactionGenerator.getNormal();
    }
}
