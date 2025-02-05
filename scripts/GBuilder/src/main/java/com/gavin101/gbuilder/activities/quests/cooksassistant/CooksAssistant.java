package com.gavin101.gbuilder.activities.quests.cooksassistant;

import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.gbuilder.activities.quests.cooksassistant.leafs.TalkToCookLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.QuestActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.quest.Quest;

public class CooksAssistant {
    private static final String activityName = "Cooks Assistant";

    public static final InventoryLoadout INVENTORY_LOADOUT = new InventoryLoadout()
            .addReq(ItemID.EGG)
            .addReq(ItemID.POT_OF_FLOUR)
            .addReq(ItemID.BUCKET_OF_MILK)
            .setLoadoutStrict();

    public static final RectArea COOK_KITCHEN_AREA = new RectArea(3209, 3216, 3211, 3213);

    public static final Branch ACTIVITY_BRANCH = new ValidateActivityBranch(
            ActivityManager.getActivity(activityName)).addLeafs(
            GetCurrentActivityLoadoutLeaf.builder()
                    .buyRemainder(true)
                    .build(),
            new IsAfkBranch().addLeafs(
                    new TalkToCookLeaf()
            )
    );

    public static final QuestActivity ACTIVITY = QuestActivity.builder()
            .name(activityName)
            .branch(ACTIVITY_BRANCH)
            .quest(Quest.COOKS_ASSISTANT)
            .inventoryLoadout(INVENTORY_LOADOUT)
            .build();
}
