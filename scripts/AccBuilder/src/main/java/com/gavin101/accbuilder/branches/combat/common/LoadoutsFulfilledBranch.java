package com.gavin101.accbuilder.branches.combat.common;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;

public class LoadoutsFulfilledBranch extends Branch {
    @Override
    public boolean isValid() {
//        Log.debug("Checking if equipment loadout is fulfilled: " +Main.currentEquipmentLoadout);
//        Log.debug(String.valueOf(Main.currentEquipmentLoadout.isFulfilled()));
//        Log.debug("Checking if inventory loadout is fulfilled: " +Main.currentInventoryLoadout);
//        Log.debug(String.valueOf(Main.currentInventoryLoadout.isFulfilled()));
//        return Main.currentEquipmentLoadout.isFulfilled() && Main.currentInventoryLoadout.isFulfilled();

        return Main.activityLoadoutsFulfilled;
    }
}
