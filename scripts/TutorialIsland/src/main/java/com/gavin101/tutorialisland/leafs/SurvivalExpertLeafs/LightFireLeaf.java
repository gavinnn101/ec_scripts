package com.gavin101.tutorialisland.leafs.SurvivalExpertLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.data.ObjectID;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.interfaces.Positionable;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.item.Item;
import net.eternalclient.api.wrappers.walking.Walking;

public class LightFireLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 80;
    }

    @Override
    public int onLoop() {
        Log.info("Lighting a fire.");
        GameObject nearestFire = GameObjects.closest(ObjectID.FIRE_26185);
        if (nearestFire == null || !nearestFire.getWorldTile().equals(Players.localPlayer().getWorldTile())) {
            Item tinderbox = Inventory.get(ItemID.TINDERBOX);
            Item logs = Inventory.get(ItemID.LOGS_2511);
            if (tinderbox != null && logs != null) {
                new InventoryEvent(tinderbox).on(logs).setEventCompleteCondition(
                        () -> Players.localPlayer().isAnimating(), Calculations.random(2000, 5000)
                ).execute();
            }
        } else {
            Log.debug("Current tile has a fire. Walking to a new tile to light fire.");
            Positionable tile = Constants.SURVIVAL_EXPERT_AREA.getRandomTile();
            Walking.walk(tile, () -> Players.localPlayer().getWorldTile().equals(tile));
        }
        return ReactionGenerator.getPredictable();
    }
}
