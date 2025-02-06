package com.gavin101.gbuilder.activities.quests.witchspotion;

import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.gbuilder.activities.quests.witchspotion.leafs.BurnMeatLeaf;
import com.gavin101.gbuilder.activities.quests.witchspotion.leafs.DrinkFromCauldronLeaf;
import com.gavin101.gbuilder.activities.quests.witchspotion.leafs.GetRatTailLeaf;
import com.gavin101.gbuilder.activities.quests.witchspotion.leafs.TalkToWitchLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.QuestActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;

public class WitchsPotion {
    private static final String activityName = "Witchs Potion";

    public static final InventoryLoadout INVENTORY_LOADOUT = new InventoryLoadout()
            .addReq(ItemID.COOKED_MEAT, 1)
            .setEnabled(() -> !Inventory.contains("Burnt meat") && Quest.WITCHS_POTION.getState() != 2)
            .addReq(ItemID.BURNT_MEAT, 1)
            .setEnabled(() -> OwnedItems.contains(ItemID.BURNT_MEAT) && Quest.WITCHS_POTION.getState() == 1)
            .addReq(ItemID.RATS_TAIL, 1)
            .setEnabled(() -> OwnedItems.contains(ItemID.RATS_TAIL) && Quest.WITCHS_POTION.getState() == 1)
            .addReq(ItemID.EYE_OF_NEWT, 1)
            .setEnabled(() -> Quest.WITCHS_POTION.getState() != 2)
            .addReq(ItemID.ONION, 1)
            .setEnabled(() -> Quest.WITCHS_POTION.getState() != 2)
            .setLoadoutStrict();

    private static Branch createBranch() {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(true)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new TalkToWitchLeaf(),
                        new BurnMeatLeaf(),
                        new GetRatTailLeaf(),
                        new DrinkFromCauldronLeaf()
                )
        );
    }

    public static final QuestActivity ACTIVITY = QuestActivity.builder()
            .name(activityName)
            .branchSupplier(WitchsPotion::createBranch)
            .quest(Quest.WITCHS_POTION)
            .inventoryLoadout(INVENTORY_LOADOUT)
            .build();
}
