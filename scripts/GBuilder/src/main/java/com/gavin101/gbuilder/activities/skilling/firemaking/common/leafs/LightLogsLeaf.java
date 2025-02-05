package com.gavin101.gbuilder.activities.skilling.firemaking.common.leafs;

import com.gavin101.gbuilder.activities.skilling.firemaking.common.constants.FiremakingConstants;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.item.ItemComposite;

@RequiredArgsConstructor
public class LightLogsLeaf extends Leaf {
    private final int logID;

    @Override
    public boolean isValid() {
        return Inventory.contains(ItemID.TINDERBOX, logID) && FiremakingConstants.atValidFiremakingTile();
    }

    @Override
    public int onLoop() {
        Log.info("Lighting logs: " + ItemComposite.getItem(logID).getName());
        new InventoryEvent(Inventory.get("Tinderbox")).on(Inventory.get(logID)
        ).setEventCompleteCondition(
                () -> Players.localPlayer().getAnimation() == FiremakingConstants.FIREMAKING_ANIMATION, Calculations.random(2000, 5000)
        ).execute();
        return ReactionGenerator.getPredictable();
    }
}
