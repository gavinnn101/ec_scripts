package com.gavin101.amethystaio.leafs.CommonLeafs.MiningLeafs;

import com.gavin101.amethystaio.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;

import java.util.List;

public class ChooseMiningSpotLeaf extends Leaf {

    private final List<WorldTile> miningSpots;
    private final RectArea miningArea;

    public ChooseMiningSpotLeaf(List<WorldTile> miningSpots, RectArea miningArea) {
        this.miningSpots = miningSpots;
        this.miningArea = miningArea;
    }

    @Override
    public boolean isValid() {
        return Main.miningSpot == null
                || (!miningSpots.contains(Main.miningSpot) && miningArea.contains(Players.localPlayer()));
    }

    @Override
    public int onLoop() {
        for (WorldTile miningSpot : miningSpots) {
            boolean spotIsEmpty = Players.all(player -> player.getWorldTile().equals(miningSpot)).isEmpty();

            if (spotIsEmpty) {
                Log.info("Setting mining guild iron spot to: " + miningSpot);
                Main.miningSpot = miningSpot;
                return ReactionGenerator.getPredictable();
            }
        }

        Log.warn("No empty mining spots found. World hopping.");
        Main.needToHopWorlds = true;

        return ReactionGenerator.getPredictable();
    }
}
