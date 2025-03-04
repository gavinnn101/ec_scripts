package com.gavin101.gbuilder.activities.quests.restlessghost;

import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.gbuilder.activities.quests.restlessghost.branches.TalkToGhostBranch;
import com.gavin101.gbuilder.activities.quests.restlessghost.leafs.*;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.QuestActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;

public class RestlessGhost {
    private static final String activityName = "Restless Ghost";

    private static final EquipmentLoadout EQUIPMENT_LOADOUT = new EquipmentLoadout()
            .addAmulet(ItemID.GHOSTSPEAK_AMULET)
            .setEnabled(() -> OwnedItems.contains(ItemID.GHOSTSPEAK_AMULET)
            );

    private static final InventoryLoadout INVENTORY_LOADOUT = new InventoryLoadout()
            .addReq(ItemID.GHOSTSPEAK_AMULET)
            .setEnabled(() -> OwnedItems.contains(ItemID.GHOSTSPEAK_AMULET) && !EQUIPMENT_LOADOUT.isFulfilled())
            .addReq(ItemID.GHOSTS_SKULL)
            .setEnabled(() -> OwnedItems.contains(ItemID.GHOSTS_SKULL))
            .setLoadoutStrict();

    private static Branch createBranch() {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(true)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new TalkToFatherAereckLeaf(),
                        new TalkToFatherUrhneyLeaf(),
                        new TalkToGhostBranch().addLeafs(
                                new GiveSkullLeaf(),
                                new OpenCoffinLeaf(),
                                new TalkToGhostLeaf()
                        ),
                        new GetSkullLeaf()
                )
        );
    }

    public static final QuestActivity ACTIVITY = QuestActivity.builder()
            .name(activityName)
            .branchSupplier(RestlessGhost::createBranch)
            .quest(Quest.THE_RESTLESS_GHOST)
            .equipmentLoadout(EQUIPMENT_LOADOUT)
            .inventoryLoadout(INVENTORY_LOADOUT)
            .build();
}
