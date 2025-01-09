package com.gavin101.accbuilder.leafs.fishing;

import com.gavin101.accbuilder.constants.fishing.FlyFishing;
import com.gavin101.accbuilder.constants.fishing.ShrimpFishing;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.walking.Walking;

public class StartFishingLeaf extends Leaf {
    private final String interaction;

    public StartFishingLeaf(String interaction) {
        this.interaction = interaction;
    }

    @Override
    public boolean isValid() {
        return !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        Log.info("Finding fishing spot to interact with.");
        Area fishArea = getFishingSpotArea();
        NPC fishingSpot = NPCs.closest(i ->
                i.hasName(getFishingSpotName(interaction))
                && fishArea.contains(i)
        );
        if (fishingSpot != null) {
            Log.debug("Found non-null fishing spot");
            if (fishingSpot.canReach()) {
                Log.debug("Clicking fishing spot.");
                new EntityInteractEvent(fishingSpot, interaction).setEventCompleteCondition(
                        () -> Players.localPlayer().isInteractingWith(fishingSpot), Calculations.random(2500, 5000)
                ).execute();
            }
        } else {
            Log.info("Couldn't find a fishing spot, walking to fishing area.");
            Walking.walk(fishArea.getRandomTile());
        }
        return ReactionGenerator.getAFK();
    }

    private String getFishingSpotName(String interaction) {
        switch (interaction) {
            default:
            case "Net":
            case "Bait":
                return "Fishing spot";
            case "Lure":
                return "Rod Fishing spot";
        }
    }

    private Area getFishingSpotArea() {
        switch (interaction) {
            default:
            case "Net":
            case "Bait":
                return ShrimpFishing.LUMBRIDGE_FISHING_AREA;
            case "Lure":
                return FlyFishing.BARB_FLY_FISHING_AREA;
        }
    }
}
