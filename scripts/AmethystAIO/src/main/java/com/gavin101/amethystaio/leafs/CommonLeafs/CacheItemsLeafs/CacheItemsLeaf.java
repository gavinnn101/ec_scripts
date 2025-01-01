package com.gavin101.amethystaio.leafs.CommonLeafs.CacheItemsLeafs;

import com.gavin101.amethystaio.Main;
import net.eternalclient.api.containers.bank.Bank;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;

public class CacheItemsLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !Main.itemsCached;
    }

    @Override
    public int onLoop() {
        Log.info("Caching bank items.");
        if (!Bank.isOpen()) {
            if (Bank.openClosest()) {
                Log.debug("Opening closest bank.");
                MethodProvider.sleepUntil(Bank::isOpen, Calculations.random(5_000, 30_000));
            }
        } else {
            Log.debug("Bank has been opened, items should be cached.");
            if (Bank.close()) {
                Log.debug("Closing bank and setting Main.itemsCached = true");
                MethodProvider.sleepUntil(() -> !Bank.isOpen(), Calculations.random(1250, 2500));
                Main.itemsCached = true;
            }
        }
        return ReactionGenerator.getNormal();
    }
}
