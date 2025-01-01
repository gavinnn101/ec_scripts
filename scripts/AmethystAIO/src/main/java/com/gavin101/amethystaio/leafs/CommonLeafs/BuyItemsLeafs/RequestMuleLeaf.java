package com.gavin101.amethystaio.leafs.CommonLeafs.BuyItemsLeafs;

import com.gavin101.amethystaio.Constants;
import com.gavin101.amethystaio.GLib;
import com.gavin101.amethystaio.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.walking.Walking;

public class RequestMuleLeaf extends Leaf {



    private static final int coinAmount = 50_000;
    @Override
    public boolean isValid() {
        return !GLib.hasGoldToBuyAllItems(Constants.MINING_BRANCH_ITEMS);
    }

    @Override
    public int onLoop() {
        if (Main.muleArea.contains(Players.localPlayer())) {
            GLib.muleRequest(coinAmount);
        } else {
            Log.debug("Walking to Grand exchange for mule request.");
            Walking.walk(Main.muleArea.getRandomTile(),
                    () -> Main.muleArea.contains(Players.localPlayer())
            );
        }
        return ReactionGenerator.getNormal();
    }
}
