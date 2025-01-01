package com.gavin101.accbuilder.leafs.fishing;

import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;

public class StartFishingLeaf extends Leaf {
    private final String interaction;

    public StartFishingLeaf(String interaction) {
        this.interaction = interaction;
    }

    @Override
    public boolean isValid() {
        return !Players.localPlayer().isAnimating()
                && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        Log.info("Finding fishing spot to interact with.");
        NPC fishingSpot = NPCs.closest("Fishing spot");
        if (fishingSpot != null && fishingSpot.canReach()) {
            Log.debug("Clicking fishing spot.");
            new EntityInteractEvent(fishingSpot, interaction).setEventCompleteCondition(
                    () -> Players.localPlayer().isInteractingWith(fishingSpot), Calculations.random(2500, 5000)
            ).execute();
        }
        return ReactionGenerator.getAFK();
    }
}
