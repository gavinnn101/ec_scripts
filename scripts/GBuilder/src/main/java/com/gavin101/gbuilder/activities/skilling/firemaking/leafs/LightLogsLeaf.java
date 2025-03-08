package com.gavin101.gbuilder.activities.skilling.firemaking.leafs;

import com.gavin101.gbuilder.activities.skilling.firemaking.Firemaking;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.item.Item;
import net.eternalclient.api.wrappers.item.ItemComposite;
import net.eternalclient.api.wrappers.map.WorldTile;

@RequiredArgsConstructor
public class LightLogsLeaf extends Leaf {
    private final int logID;

    @Override
    public boolean isValid() {
        return Inventory.containsAll(ItemID.TINDERBOX, logID) && Firemaking.atValidFiremakingTile();
    }

    @Override
    public int onLoop() {
        Log.info("Lighting logs: " + ItemComposite.getItem(logID).getName());
        WorldTile originalPlayerTile = Players.localPlayer().getWorldTile();
        Item tinderbox = Inventory.get(ItemID.TINDERBOX);
        Item logs = Inventory.get(logID);
        if (tinderbox != null && logs != null) {
            new InventoryEvent(tinderbox).on(logs).setEventCompleteCondition(
                    () -> {
                        GameObject playerTileFire = GameObjects.closest(i -> i.hasName("Fire")
                                && i.getWorldTile().equals(originalPlayerTile)
                        );
                        return playerTileFire != null;
                    }, Calculations.random(2000, 7000)
            ).execute();
        }
        return FatigueTracker.getCurrentReactionTime();
//        return ReactionGenerator.getPredictable();
    }
}
