package com.gavin101.gbuilder.activities.misc.tanning;

import com.gavin101.GLib.GLib;
import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.GLib.leafs.common.GoToAreaLeaf;
import com.gavin101.gbuilder.activities.misc.tanning.constants.TanningConstants;
import com.gavin101.gbuilder.activities.misc.tanning.leafs.TanHidesLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.Activity;
import com.gavin101.gbuilder.activitymanager.activity.MiscActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;

public class Tanning {
    @Getter
    @RequiredArgsConstructor
    public enum LeatherType {
        LEATHER(GLib.getItemName(ItemID.LEATHER), ItemID.LEATHER, ItemID.COWHIDE, 1),
        HARD_LEATHER(GLib.getItemName(ItemID.HARD_LEATHER), ItemID.HARD_LEATHER, ItemID.COWHIDE, 3),
        SNAKESKIN(GLib.getItemName(ItemID.SNAKESKIN), ItemID.SNAKESKIN, ItemID.SNAKE_HIDE, 45),
        GREEN_DRAGON_LEATHER(GLib.getItemName(ItemID.GREEN_DRAGON_LEATHER), ItemID.GREEN_DRAGON_LEATHER, ItemID.GREEN_DRAGONHIDE, 57),
        BLUE_DRAGON_LEATHER(GLib.getItemName(ItemID.BLUE_DRAGON_LEATHER), ItemID.BLUE_DRAGON_LEATHER, ItemID.BLUE_DRAGONHIDE, 66),
        RED_DRAGON_LEATHER(GLib.getItemName(ItemID.RED_DRAGON_LEATHER), ItemID.RED_DRAGON_LEATHER, ItemID.RED_DRAGONHIDE, 73),
        BLACK_DRAGON_LEATHER(GLib.getItemName(ItemID.BLACK_DRAGON_LEATHER), ItemID.BLACK_DRAGON_LEATHER, ItemID.BLACK_DRAGONHIDE, 79);

        private final String name;
        private final int itemId;
        private final int rawMaterialId;
        private final int cost;
    }

    public static Activity createActivity(LeatherType leatherType) {
        String activityName = String.format("Tanning material: %s to make: %s", GLib.getItemName(leatherType.getRawMaterialId()), leatherType.getName());

        InventoryLoadout inventoryLoadout = new InventoryLoadout()
                .addReq(ItemID.COINS_995, () -> getTotalTanningCost(leatherType))
                .addReq(leatherType.getRawMaterialId(), () -> Math.min(OwnedItems.count(leatherType.getRawMaterialId()), 27));

        return MiscActivity.builder()
                .name(activityName)
                .branchSupplier(() -> createBranch(leatherType, activityName))
                .inventoryLoadout(inventoryLoadout)
                .validator(() -> OwnedItems.contains(leatherType.getRawMaterialId()) && OwnedItems.count(ItemID.COINS_995) > getTotalTanningCost(leatherType))
                .build();
    }

    private static Branch createBranch(LeatherType leatherType, String activityName) {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(false)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new GoToAreaLeaf(TanningConstants.ALKHARID_TANNER_AREA),
                        new TanHidesLeaf(leatherType)
                )
        );
    }

    private static int getTotalTanningCost(LeatherType leatherType) {
        int materialAmount = OwnedItems.count(leatherType.getRawMaterialId());
        return materialAmount * leatherType.getCost();
    }
}
