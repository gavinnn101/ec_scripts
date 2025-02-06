package com.gavin101.gbuilder.activities.quests.goblindiplomacy;

import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.gbuilder.activities.quests.goblindiplomacy.leafs.MakeArmorLeaf;
import com.gavin101.gbuilder.activities.quests.goblindiplomacy.leafs.TalkToGeneralLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.QuestActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class GoblinDiplomacy {
    private static final String activityName = "Goblin Diplomacy";

    public static final InventoryLoadout INVENTORY_LOADOUT = new InventoryLoadout()
            .addReq(ItemID.GOBLIN_MAIL, 3)
            .setEnabled(() -> (!Inventory.contains(ItemID.GOBLIN_MAIL)) && Quest.GOBLIN_DIPLOMACY.isNotStarted())
            .addReq(ItemID.ORANGE_DYE, 1)
            .setEnabled(() -> !Inventory.contains(ItemID.ORANGE_GOBLIN_MAIL) && Quest.GOBLIN_DIPLOMACY.isNotStarted())
            .addReq(ItemID.BLUE_DYE, 1)
            .setEnabled(() -> !Inventory.contains(ItemID.BLUE_GOBLIN_MAIL) && Quest.GOBLIN_DIPLOMACY.isNotStarted()
            );

    private static Branch createBranch() {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(true)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new MakeArmorLeaf(),
                        new TalkToGeneralLeaf()
                )
        );
    }

    public static final QuestActivity ACTIVITY = QuestActivity.builder()
            .name(activityName)
            .branchSupplier(GoblinDiplomacy::createBranch)
            .quest(Quest.GOBLIN_DIPLOMACY)
            .inventoryLoadout(INVENTORY_LOADOUT)
            .build();
}
