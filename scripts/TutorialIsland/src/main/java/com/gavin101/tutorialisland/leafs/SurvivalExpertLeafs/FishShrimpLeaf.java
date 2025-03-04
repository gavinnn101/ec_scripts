package com.gavin101.tutorialisland.leafs.SurvivalExpertLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.data.NpcID;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;

public class FishShrimpLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 40;
    }

    @Override
    public int onLoop() {
        Log.info("Interacting with fishing spot.");
        NPC netFishingSpot = NPCs.closest(NpcID.FISHING_SPOT_3317);
        if (netFishingSpot != null && netFishingSpot.canReach()) {
            Log.debug("Clicking fishing spot.");
            new EntityInteractEvent(netFishingSpot, "Net").setEventCompleteCondition(
                    () -> Players.localPlayer().isInteractingWith(netFishingSpot), Calculations.random(2500, 5000)
            ).execute();
        }
        return ReactionGenerator.getAFK();
    }
}
