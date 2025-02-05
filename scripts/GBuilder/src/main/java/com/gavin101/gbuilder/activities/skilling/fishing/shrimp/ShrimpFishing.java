package com.gavin101.gbuilder.activities.skilling.fishing.shrimp;

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
import net.eternalclient.api.wrappers.skill.Skill;

public class ShrimpFishing {
    private static final String activityName = "Fish shrimp";

    public static final InventoryLoadout INVENTORY_LOADOUT = new InventoryLoadout()
            .addReq(ItemID.SMALL_FISHING_NET)
            .setLoadoutStrict(Inventory::isFull);

    public static final Branch ACTIVITY_BRANCH = new ValidateActivityBranch(
            ActivityManager.getActivity(activityName)).addLeafs(
            GetCurrentActivityLoadoutLeaf.builder()
                    .buyRemainder(true)
                    .build(),
            new IsAfkBranch().addLeafs(
                    new GoToAreaLeaf(GLib.getRandomArea(Fishing.FISHING_TYPE_TO_AREAS_MAP(Fishing.FishingType.SMALL_FISHING_NET))),
                    new StartFishingLeaf(Fishing.FishingType.SMALL_FISHING_NET)
            )
    );

    public static final SkillActivity ACTIVITY = SkillActivity.builder()
            .name(activityName)
            .branch(ACTIVITY_BRANCH)
            .activitySkill(Skill.FISHING)
            .inventoryLoadout(INVENTORY_LOADOUT)
            .minLevel(1)
            .maxLevel(99)
            .build();
}
