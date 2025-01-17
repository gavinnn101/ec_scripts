package com.gavin101.GLib.leafs.common;

import com.gavin101.GLib.GLib;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.walking.Walking;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class RequestMuleLeaf extends Leaf {
    private final Area muleArea = new RectArea(3146, 3507, 3182, 3470);

    private final Supplier<Boolean> validator;

    @Override
    public boolean isValid() {
        return validator.get();
    }

    @Override
    public int onLoop() {
        int coinAmount = 100_000;
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
