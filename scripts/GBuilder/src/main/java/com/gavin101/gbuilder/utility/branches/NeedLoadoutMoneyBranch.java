package com.gavin101.gbuilder.utility.branches;

import com.gavin101.gbuilder.activitymanager.ActivityManager;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.container.OwnedItems;

public class NeedLoadoutMoneyBranch extends Branch {
    @Override
    public boolean isValid() {
        int equipmentCost = ActivityManager.getCurrentActivity().getEquipmentLoadout().getTotalPrice();
        int inventoryCost = ActivityManager.getCurrentActivity().getInventoryLoadout().getTotalPrice();
        int totalCost = equipmentCost + inventoryCost;

        int ownedCoinsAmount = OwnedItems.count(ItemID.COINS_995);
        if (ownedCoinsAmount < totalCost) {
            Log.debug("Our amount of coins: " +ownedCoinsAmount +" is less than whats needed to fulfill our loadouts: " +totalCost);
            Log.debug("Need to sell items or request gp from mule.");
            return true;
        }
        return false;
//        return OwnedItems.count(ItemID.COINS_995) < totalCost;
    }
}
