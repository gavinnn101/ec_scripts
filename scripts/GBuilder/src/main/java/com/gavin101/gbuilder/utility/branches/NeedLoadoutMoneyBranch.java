package com.gavin101.gbuilder.utility.branches;

import com.gavin101.gbuilder.activitymanager.ActivityManager;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;

public class NeedLoadoutMoneyBranch extends Branch {
    @Override
    public boolean isValid() {
        int equipmentCost = ActivityManager.getCurrentActivity().getEquipmentLoadout().getTotalPrice();
        int inventoryCost = ActivityManager.getCurrentActivity().getInventoryLoadout().getTotalPrice();
        int totalCost = equipmentCost + inventoryCost;
        return OwnedItems.count(ItemID.COINS_995) < totalCost;
    }
}
