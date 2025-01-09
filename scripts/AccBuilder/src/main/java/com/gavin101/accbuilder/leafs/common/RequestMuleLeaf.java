package com.gavin101.accbuilder.leafs.common;

import com.gavin101.GLib.GLib;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.walking.Walking;

public class RequestMuleLeaf extends Leaf {
    private final Area muleArea = new RectArea(3146, 3507, 3182, 3470);

    @Override
    public boolean isValid() {
        // This should probably be replaced by a flag set when we fail to purchase an inventory loadout.
        // Not sure how to do that yet.
        return OwnedItems.count(ItemID.COINS_995) < 5000;
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
