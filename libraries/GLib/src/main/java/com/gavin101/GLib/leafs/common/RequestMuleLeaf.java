package com.gavin101.GLib.leafs.common;

import com.gavin101.GLib.GLib;
import lombok.Builder;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.walking.Walking;

import java.util.function.Supplier;

@Builder
public class RequestMuleLeaf extends Leaf {
    @Builder.Default
    private Area muleArea = new RectArea(3146, 3507, 3182, 3470);
    @Builder.Default
    private int coinAmount = 100_000;
    @Builder.Default
    private Supplier<Boolean> validator = () -> true;

    @Override
    public boolean isValid() {
        return validator.get();
    }

    @Override
    public int onLoop() {
        if (muleArea.contains(Players.localPlayer())) {
            GLib.muleRequest(coinAmount);
        } else {
            Log.debug("Walking to mule area to request gold amount: " + coinAmount);
            Walking.walk(muleArea.getRandomTile(),
                    () -> muleArea.contains(Players.localPlayer())
            );
        }
        return ReactionGenerator.getNormal();
    }
}
