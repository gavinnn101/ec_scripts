package com.gavin101.mule.leafs;

import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.events.muling.MuleHandleEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.Area;

public class MuleLeaf extends Leaf {

    private final Area muleArea;

    public MuleLeaf(Area muleArea) {
        this.muleArea = muleArea;
    }

    @Override
    public boolean isValid() {
        return muleArea.contains(Players.localPlayer());
    }

    @Override
    public int onLoop() {
        muleStart();
        return ReactionGenerator.getNormal();
    }

    public void muleStart() {
        Log.info("Starting mule");
        new MuleHandleEvent().execute();
    }
}
