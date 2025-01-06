package com.gavin101.test.accbuilder.tasks.leveling.fishing.tierone.leafs;

import com.gavin101.events.EntityHelperEvent;
import com.gavin101.test.accbuilder.tasks.leveling.fishing.Common;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class TierOneFishingLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !Players.localPlayer().isAnimating()
                && !Players.localPlayer().isMoving()
                && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        Log.info("Finding fishing spot to interact with.");
        String interaction = "Net";
        String fishingSpotName = Common.getFishingSpotName(interaction);
        new EntityHelperEvent(
                () -> NPCs.closest(fishingSpotName),
                interaction,
                () -> Players.localPlayer().isInteractingWith(NPCs.closest(fishingSpotName)),
                Common.getFishingSpotArea(interaction)
        ).execute();
        return ReactionGenerator.getAFK();
    }
}

