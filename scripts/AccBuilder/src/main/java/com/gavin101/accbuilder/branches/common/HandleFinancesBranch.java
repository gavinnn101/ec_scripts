package com.gavin101.accbuilder.branches.common;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.container.OwnedItems;

public class HandleFinancesBranch extends Branch {

    @Override
    public boolean isValid() {
        if (!Main.activityLoadoutsFulfilled) {
            // Get total cost of missing equipment and items from loadouts.
            int equipmentCost = Main.currentEquipmentLoadout.getTotalPrice();
            int inventoryCost = Main.currentInventoryLoadout.getTotalPrice();
            int totalCost = equipmentCost + inventoryCost;
            Log.debug("Total cost of loadouts: " +totalCost);
            return OwnedItems.count(ItemID.COINS_995) < totalCost;
        }
        return false;
    }
}
