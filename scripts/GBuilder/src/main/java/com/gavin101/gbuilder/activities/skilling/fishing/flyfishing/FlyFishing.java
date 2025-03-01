package com.gavin101.gbuilder.activities.skilling.fishing.flyfishing;

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

public class FlyFishing {
    private static final String activityName = "Fly fishing";

    private static final int minFeathers = Calculations.random(30, 100);

    public static final InventoryLoadout INVENTORY_LOADOUT = new InventoryLoadout()
            .addReq(ItemID.FLY_FISHING_ROD)
            .addReq(ItemID.FEATHER, Calculations.random(1000, 2001)) // Withdraw between 1000-2000 feathers
            .setStrict(Inventory.count(ItemID.FEATHER) < minFeathers)
            .setRefill(Calculations.random(3000, 4001)) // Buy between 3000-4000 feathers if we don't have enough for our withdraw amount
            .setLoadoutStrict(Inventory::isFull);


    private static Branch createBranch() {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(true)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new GoToAreaLeaf(GLib.getRandomArea(Fishing.FISHING_TYPE_TO_AREAS_MAP(Fishing.FishingType.FLY_FISHING))),
                        new StartFishingLeaf(Fishing.FishingType.FLY_FISHING)
                )
        );
    }

    public static final SkillActivity ACTIVITY = SkillActivity.builder()
            .name(activityName)
            .branchSupplier(FlyFishing::createBranch)
            .activitySkill(Skill.FISHING)
            .inventoryLoadout(INVENTORY_LOADOUT)
            .minLevel(20)
            .maxLevel(Calculations.random(50, 60))
            .build();
}
