package com.gavin101.amethystaio.leafs.CommonLeafs.MiningLeafs;

import com.gavin101.amethystaio.branches.FreeToPlayBranches.MiningBranches.UpgradePickaxeBranch;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.events.loadout.WithdrawLoadoutEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;

public class UpgradePickaxeLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return UpgradePickaxeBranch.getBestPickaxeID() != UpgradePickaxeBranch.getCurrentPickaxeID();
    }

    @Override
    public int onLoop() {
        Log.info("Upgrading to new best pickaxe.");
        int bestPickaxeID = UpgradePickaxeBranch.getBestPickaxeID();
        InventoryLoadout invyLoadout = new InventoryLoadout().addReq(bestPickaxeID);
        new WithdrawLoadoutEvent(invyLoadout).setEventCompleteCondition(
                () -> Inventory.contains(bestPickaxeID)
        ).execute();
        return ReactionGenerator.getNormal();
    }
}
