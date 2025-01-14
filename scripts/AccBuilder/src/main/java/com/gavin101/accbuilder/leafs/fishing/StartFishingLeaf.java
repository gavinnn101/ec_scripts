package com.gavin101.accbuilder.leafs.fishing;

import com.gavin101.accbuilder.constants.fishing.Fishing;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;

@RequiredArgsConstructor
public class StartFishingLeaf extends Leaf {
    private final Fishing.FishingType fishingType;

    @Override
    public boolean isValid() {
        return !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        Log.info("Finding fishing spot to interact with.");
        String[] fishingSpotActions = getFishingTypeActions(fishingType);
        NPC fishingSpot = NPCs.closest(i -> i.hasAction(fishingSpotActions));

        if (fishingSpot != null && fishingSpot.canReach()) {
            Log.debug("Clicking fishing spot.");
            new EntityInteractEvent(fishingSpot, fishingSpotActions).setEventCompleteCondition(
                    () -> Players.localPlayer().isInteractingWith(fishingSpot), Calculations.random(2500, 5000)
            ).execute();
        }
        return ReactionGenerator.getAFK();
    }

    private String[] getFishingTypeActions(Fishing.FishingType fishingType) {
        String[] defaultFishingTypeActions = {};
        switch (fishingType) {
            case SMALL_FISHING_NET:
                return new String[] {"Net", "Small Net"};
            case BAIT_FISHING:
                return new String[] {"Bait"};
            case FLY_FISHING:
                return new String[] {"Lure"};
        }
        return defaultFishingTypeActions;
    }
}
