package com.gavin101.gbuilder.activities.quests.doricsquest;

import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.gbuilder.activities.quests.doricsquest.leafs.TalkToDoricLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.QuestActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.quest.Quest;

public class DoricsQuest {
    private static final String activityName = "Dorics Quest";

    public static final InventoryLoadout INVENTORY_LOADOUT = new InventoryLoadout()
            .addReq(ItemID.CLAY, 6)
            .addReq(ItemID.COPPER_ORE, 4)
            .addReq(ItemID.IRON_ORE, 2)
            .setLoadoutStrict();

    public static final RectArea DORICS_HOUSE_AREA = new RectArea(2949, 3452, 2953, 3449);

    public static final Branch ACTIVITY_BRANCH = new ValidateActivityBranch(
            ActivityManager.getActivity(activityName)).addLeafs(
            GetCurrentActivityLoadoutLeaf.builder()
                    .buyRemainder(true)
                    .build(),
            new IsAfkBranch().addLeafs(
                    new TalkToDoricLeaf()
            )
    );

    public static final QuestActivity ACTIVITY = QuestActivity.builder()
            .name(activityName)
            .branch(ACTIVITY_BRANCH)
            .quest(Quest.DORICS_QUEST)
            .inventoryLoadout(INVENTORY_LOADOUT)
            .build();
}
