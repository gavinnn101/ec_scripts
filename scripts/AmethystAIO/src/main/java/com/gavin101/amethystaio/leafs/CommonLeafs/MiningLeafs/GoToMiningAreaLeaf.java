package com.gavin101.amethystaio.leafs.CommonLeafs.MiningLeafs;

import com.gavin101.amethystaio.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.walking.Walking;

import java.util.List;

public class GoToMiningAreaLeaf extends Leaf {

    private final List<WorldTile> miningSpots;
    private final RectArea miningArea;

    public GoToMiningAreaLeaf(List<WorldTile> miningSpots, RectArea miningArea) {
        this.miningSpots = miningSpots;
        this.miningArea = miningArea;
    }

    @Override
    public boolean isValid() {
        return (Main.miningSpot == null || !miningSpots.contains(Main.miningSpot))
                && !miningArea.contains(Players.localPlayer());
    }

    @Override
    public int onLoop() {
        Log.info("Walking to mining area.");
        Walking.walk(miningArea.getRandomTile(),
                () -> miningArea.contains(Players.localPlayer())
        );
        return ReactionGenerator.getNormal();
    }
}
