package com.gavin101.progressiveminer.leafs;

import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;


public class MineRockLeaf extends Leaf {

    private final String rockToMine;

    public MineRockLeaf(String rockToMine) {
        this.rockToMine = rockToMine;
    }

    private GameObject lastRock = null;
    @Override
    public boolean isValid() {
        return !Inventory.isFull()
                && (!Players.localPlayer().isAnimating() || GameObjects.closest(i -> i.getWorldTile().equals(lastRock.getWorldTile()) && i.hasName("Rocks")) != null);
    }

    @Override
    public int onLoop() {
        Log.info("Mining rock: " +rockToMine);
        GameObject ironRock = GameObjects.closest(
                i -> i.hasName(rockToMine) && i.distance() <= 1
        );
        if (ironRock != null) {
            new EntityInteractEvent(ironRock, "Mine").setEventCompleteCondition(
                    () -> Players.localPlayer().isAnimating(),
                    Calculations.random(1250, 2500)
            ).execute();
            lastRock = ironRock;
        }
        return ReactionGenerator.getNormal();
    }
}
