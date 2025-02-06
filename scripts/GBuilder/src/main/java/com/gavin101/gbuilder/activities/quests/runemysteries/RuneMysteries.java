package com.gavin101.gbuilder.activities.quests.runemysteries;

import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.gbuilder.activities.quests.runemysteries.leafs.TalkToAuburyLeaf;
import com.gavin101.gbuilder.activities.quests.runemysteries.leafs.TalkToDukeLeaf;
import com.gavin101.gbuilder.activities.quests.runemysteries.leafs.TalkToSedridorLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.QuestActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class RuneMysteries {
    private static final String activityName = "Rune mysteries";

    public static final InventoryLoadout INVENTORY_LOADOUT = new InventoryLoadout()
            .addReq(ItemID.AIR_TALISMAN)
            .setEnabled(() -> Quest.RUNE_MYSTERIES.getState() == 1)
            .addReq(ItemID.RESEARCH_PACKAGE)
            .setEnabled(() -> Quest.RUNE_MYSTERIES.getState() == 3)
            .addReq(ItemID.RESEARCH_NOTES)
            .setEnabled(() -> Quest.RUNE_MYSTERIES.getState() == 5)
            .setLoadoutStrict();

    private static Branch createBranch() {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(true)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new TalkToDukeLeaf(),
                        new TalkToSedridorLeaf(),
                        new TalkToAuburyLeaf()
                )
        );
    }

    public static final QuestActivity ACTIVITY = QuestActivity.builder()
            .name(activityName)
            .branchSupplier(RuneMysteries::createBranch)
            .quest(Quest.RUNE_MYSTERIES)
            .inventoryLoadout(INVENTORY_LOADOUT)
            .build();
}
