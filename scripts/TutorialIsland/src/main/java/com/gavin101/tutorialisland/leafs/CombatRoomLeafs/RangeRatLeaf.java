package com.gavin101.tutorialisland.leafs.CombatRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.equipment.Equipment;
import net.eternalclient.api.data.NpcID;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.walking.Walking;

public class RangeRatLeaf extends Leaf {
    @Override
    public boolean isValid() {
        int tutorialProgress = PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR);
        return (tutorialProgress == 480 && Equipment.contains(Constants.rangeRatEquipmentLoadout.getIDs()))
                || tutorialProgress == 490
                && !Players.localPlayer().isInCombat();
    }

    @Override
    public int onLoop() {
        Log.info("Fighting a rat with range.");
        NPC rat = NPCs.closest(g -> g.hasID(NpcID.GIANT_RAT_3313)
                && Players.localPlayer().hasLineOfSightTo(g)
                && !g.isInCombat()
        );
        if (rat != null) {
            Log.debug("Interacting with rat 'Attack'.");
            new EntityInteractEvent(rat, "Attack").setEventCompleteCondition(
                    () -> Players.localPlayer().isInCombat(), Calculations.random(1500, 3500)
            ).execute();
        } else if (!Players.localPlayer().isInCombat()) {
                Log.debug("Didn't find a rat in LOS. Moving to a different tile.");
                Walking.localWalk(Constants.COMBAT_INSTRUCTOR_AREA.getRandomTile());
        }
        return ReactionGenerator.getNormal();
    }
}
