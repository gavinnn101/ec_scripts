package com.gavin101.GLib.leafs.common;

import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.walking.Walking;

@RequiredArgsConstructor
public class GoToAreaLeaf extends Leaf {
    private final Area area;

    @Override
    public boolean isValid() {
        return !area.contains(Players.localPlayer());
    }

    @Override
    public int onLoop() {
        Log.info("Walking to area");
        Walking.walk(area.getRandomTile());
        Log.info("Walk to area finished");
        return ReactionGenerator.getNormal();
    }
}
