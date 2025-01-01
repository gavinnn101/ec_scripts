package com.gavin101.amethystaio.branches.FreeToPlayBranches.MiningBranches;

import com.gavin101.amethystaio.Constants;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.containers.equipment.Equipment;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class UpgradePickaxeBranch extends Branch {
    @Override
    public boolean isValid() {
        return getCurrentPickaxeID() != getBestPickaxeID();
    }

    public static int getCurrentPickaxeID() {
        for (Map.Entry<Integer, Integer> entry : Constants.MINING_BRANCH_ITEMS.entrySet()) {
            int pickaxeId = entry.getKey();
            if (Equipment.contains(pickaxeId) || Inventory.contains(pickaxeId)) {
                return pickaxeId;
            }
        }
        Log.warn("No pickaxe found in equipment or inventory");
        return -1;
    }


    public static int getBestPickaxeID() {
        Log.debug("Getting best pickaxe for our level.");
        int miningLevel = Skills.getRealLevel(Skill.MINING);
        int bestPickaxeID = 0;
        if (miningLevel >= 6 && miningLevel < 21) {
            bestPickaxeID = ItemID.STEEL_PICKAXE;
        } else if (miningLevel >= 21 && miningLevel < 31) {
            bestPickaxeID = ItemID.MITHRIL_PICKAXE;
        } else if (miningLevel >= 31 && miningLevel < 41) {
            bestPickaxeID = ItemID.ADAMANT_PICKAXE;
        } else if (miningLevel >= 41) {
            bestPickaxeID = ItemID.RUNE_PICKAXE;
        }
        Log.debug("Returning best pickaxe id for our level: " +bestPickaxeID);
        return bestPickaxeID;
    }
}
