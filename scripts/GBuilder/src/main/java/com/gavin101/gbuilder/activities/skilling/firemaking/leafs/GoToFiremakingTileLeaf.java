package com.gavin101.gbuilder.activities.skilling.firemaking.leafs;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.Main;
import com.gavin101.gbuilder.activities.skilling.firemaking.Firemaking;
import com.gavin101.gbuilder.activities.skilling.firemaking.constants.FiremakingConstants;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.walking.Walking;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GoToFiremakingTileLeaf extends Leaf {
    private final FiremakingConstants.FiremakingArea firemakingArea;

    @Override
    public boolean isValid() {
        WorldTile playerTile = Players.localPlayer().getWorldTile();
        return Main.needToChangeFiremakingTile
                || !Firemaking.atValidFiremakingTile()
                || (Inventory.isFull() && !firemakingArea.getTiles().contains(playerTile));
    }

    @Override
    public int onLoop() {
        // Filter out tiles that already have a fire on them.
        List<WorldTile> validTiles = firemakingArea.getTiles().stream()
                .filter(tile -> {
                    GameObject fire = GameObjects.closest(i -> i.hasName("Fire")
                            && i.getWorldTile().equals(tile));
                    return fire == null;
                })
                .collect(Collectors.toList());

        if (validTiles.isEmpty()) {
            Log.info("No valid firemaking tiles available, setting needToHopWorlds.");
            Main.needToHopWorlds = true;
            return ReactionGenerator.getLowPredictable();
        }

        Log.info("Walking to a random, valid firemaking tile.");
        WorldTile firemakingTile = GLib.getRandomTile(validTiles);
        Walking.walkExact(firemakingTile);

        if (Players.localPlayer().getWorldTile().equals(firemakingTile)) {
            Log.info("Done walking to firemaking tile.");
            if (Main.needToChangeFiremakingTile) {
                Log.debug("Setting Main.needToChangeFiremakingTile = false");
                Main.needToChangeFiremakingTile = false;
            }
        }
        return FatigueTracker.getCurrentReactionTime();
    }
}
