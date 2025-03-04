package com.gavin101.gbuilder.utility.leafs;

import com.gavin101.GLib.GLib;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.events.ge.GrandExchangeEvent;
import net.eternalclient.api.events.ge.items.SellItem;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.container.OwnedItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SellItemsLeaf extends Leaf {
    private final Map<Integer, Integer> itemsToSell; // Map<ItemID, Quantity>

    @Override
    public boolean isValid() {
        return GLib.canSellItems() && itemsToSell.keySet().stream().anyMatch(OwnedItems::contains);
    }

    @Override
    public int onLoop() {
        Log.info("Selling items...");
        List<SellItem> sellItems = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : itemsToSell.entrySet()) {
            int itemId = entry.getKey();
//            int quantityToSell = entry.getValue();

            // Sell all of our items for now. setting a high value like 99999 doesn't work like we'd hope.
            int quantityToSell = OwnedItems.count(itemId);
            if (OwnedItems.contains(itemId)) {
                Log.debug("Adding item to sell: " + GLib.getItemName(itemId) +" of quantity: " +quantityToSell);
                sellItems.add(new SellItem(itemId, quantityToSell));
            }
        }

        if (!sellItems.isEmpty()) {
            new GrandExchangeEvent().addSellItems(sellItems).execute();
        }
        return ReactionGenerator.getNormal();
    }
}
