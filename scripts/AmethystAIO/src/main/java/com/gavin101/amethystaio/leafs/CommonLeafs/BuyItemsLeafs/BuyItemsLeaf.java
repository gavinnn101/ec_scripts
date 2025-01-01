package com.gavin101.amethystaio.leafs.CommonLeafs.BuyItemsLeafs;

import com.gavin101.amethystaio.GActivityHelper;
import com.gavin101.amethystaio.GLib;
import net.eternalclient.api.events.ge.GrandExchangeEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

import java.util.Map;

public class BuyItemsLeaf extends Leaf {

    private final Map<Integer, Integer> itemsToBuy;

    public BuyItemsLeaf(Map<Integer, Integer> itemsToBuy) {
        this.itemsToBuy = itemsToBuy;
    }

    @Override
    public boolean isValid() {
        return GActivityHelper.needToBuyItems() && GLib.hasGoldToBuyAllItems(itemsToBuy);
    }

    @Override
    public int onLoop() {
        Log.info("Buying required items.");
        Map<Integer, Integer> missingItems = GLib.getMissingItems(itemsToBuy);

        if (missingItems.isEmpty()) {
            Log.debug("No missing items, setting setNeedToBuyItems(false)");
            GActivityHelper.setNeedToBuyItems(false);
        } else {
            GrandExchangeEvent buyItemEvent = GLib.createGrandExchangeBuyEvent(missingItems);
            buyItemEvent.execute();
        }
        return ReactionGenerator.getNormal();
    }
}