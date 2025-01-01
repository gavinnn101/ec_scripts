package com.gavin101.amethystaio.branches.CommonBranches;

import com.gavin101.amethystaio.GActivityHelper;
import com.gavin101.amethystaio.GLib;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;

import java.util.Map;

public class BuyItemsBranch extends Branch {

    private final Map<Integer, Integer> requiredItems;

    public BuyItemsBranch(Map<Integer, Integer> requiredItems) {
        this.requiredItems = requiredItems;
    }

    @Override
    public boolean isValid() {
        Log.debug("needToBuyItems: " + GActivityHelper.needToBuyItems());

        if (!GActivityHelper.needToBuyItems()) {
            return false;
        }

        if (GLib.ownsRequiredItems(requiredItems)) {
            GActivityHelper.setNeedToBuyItems(false);
            return false;
        }

        return true;
    }
}