package com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.CommonQuestLeafs;

import com.gavin101.amethystaio.GActivityHelper;
import com.gavin101.amethystaio.GLib;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.events.loadout.WithdrawLoadoutEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;

import java.util.Map;

public class WithdrawItemsLeaf extends Leaf {

    private final Map<Integer, Integer> items;

    public WithdrawItemsLeaf(Map<Integer, Integer> items) {
        this.items = items;
    }

    @Override
    public boolean isValid() {
        if (!GActivityHelper.needToWithdrawItems()) {
            return false;
        }
        if (!GLib.ownsRequiredItems(items)) {
            GActivityHelper.setNeedToBuyItems(true);
            return false;
        }

        if (GLib.inventoryContainsAll(items)) {
            GActivityHelper.setNeedToWithdrawItems(false);
            return false;
        }

        return GActivityHelper.needToWithdrawItems();
    }

    @Override
    public int onLoop() {
        Log.info("Withdrawing items needed for quest.");
        InventoryLoadout inventoryLoadout = GLib.createInventoryLoadout(items);
        if (new WithdrawLoadoutEvent(inventoryLoadout).executed()) {
            MethodProvider.sleepUntil(() -> GLib.inventoryContainsAll(items), Calculations.random(5_000, 15_000));
            GActivityHelper.setNeedToWithdrawItems(false);
        }

        return ReactionGenerator.getNormal();
    }
}