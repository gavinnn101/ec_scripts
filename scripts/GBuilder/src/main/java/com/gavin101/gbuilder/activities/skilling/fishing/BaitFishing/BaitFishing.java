package com.gavin101.gbuilder.activities.skilling.fishing.BaitFishing;

import com.gavin101.GLib.GLib;
import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.GLib.leafs.common.GoToAreaLeaf;
import com.gavin101.gbuilder.activities.skilling.fishing.common.constants.Fishing;
import com.gavin101.gbuilder.activities.skilling.fishing.common.leafs.StartFishingLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.SkillActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.skill.Skill;

public class BaitFishing {
    private static final String activityName = "Bait fishing";

    public static final InventoryLoadout INVENTORY_LOADOUT = new InventoryLoadout()
            .addReq(ItemID.FISHING_ROD)
            .addReq(ItemID.FISHING_BAIT, Calculations.random(500, 751)) // Withdraw between 500-751 fishing bait
            .setEnabled(() -> !Inventory.contains(ItemID.FISHING_BAIT))
            .setRefill(Calculations.random(1000, 2001)) // Buy between 1000-2000 fishing bait if we don't have enough for our withdraw amount
            .setLoadoutStrict(Inventory::isFull);


    private static Branch createBranch() {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(true)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new GoToAreaLeaf(GLib.getRandomArea(Fishing.FISHING_TYPE_TO_AREAS_MAP(Fishing.FishingType.BAIT_FISHING))),
                        new StartFishingLeaf(Fishing.FishingType.BAIT_FISHING)
                )
        );
    }


    public static final SkillActivity ACTIVITY = SkillActivity.builder()
            .name(activityName)
            .branchSupplier(BaitFishing::createBranch)
            .activitySkill(Skill.FISHING)
            .inventoryLoadout(INVENTORY_LOADOUT)
            .minLevel(5)
            .maxLevel(20)
            .build();
}
