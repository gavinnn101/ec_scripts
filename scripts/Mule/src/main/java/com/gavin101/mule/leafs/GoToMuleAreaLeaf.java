package com.gavin101.mule.leafs;

import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.walking.Walking;

public class GoToMuleAreaLeaf extends Leaf {

    private final Area muleArea;

    public GoToMuleAreaLeaf(Area muleArea) {
        this.muleArea = muleArea;
    }

    @Override
    public boolean isValid() {
        return !muleArea.contains(Players.localPlayer());
    }

    @Override
    public int onLoop() {
        Log.info("Walking to mule area.");
        Walking.walk(muleArea.getRandomTile(),
                () -> muleArea.contains(Players.localPlayer())
        );
        return ReactionGenerator.getNormal();
    }
}
