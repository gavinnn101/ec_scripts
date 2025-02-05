package com.gavin101.gbuilder.activities.skilling.firemaking.common;

import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.gbuilder.activities.skilling.firemaking.common.constants.FiremakingConstants;
import com.gavin101.gbuilder.activities.skilling.firemaking.common.leafs.GoToFiremakingTileLeaf;
import com.gavin101.gbuilder.activities.skilling.firemaking.common.leafs.LightLogsLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.SkillActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.item.ItemComposite;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.List;

public class Firemaking {
    public static SkillActivity createActivity(int logId) {
        String activityName = String.format("Firemaking %s", ItemComposite.getItem(logId).getName());

        List<WorldTile> firemakingTiles = FiremakingConstants.getRandomFiremakingTileSet();

        InventoryLoadout inventoryLoadout = new InventoryLoadout()
                .addReq(ItemID.TINDERBOX)
                .addReq(logId, () -> Math.min(OwnedItems.count(logId), 27))
                .setEnabled(() -> !Inventory.contains(logId))
                .setStrict(true);

        return SkillActivity.builder()
                .name(activityName)
                .branchSupplier(() -> createBranch(logId, activityName, firemakingTiles))
                .activitySkill(Skill.FIREMAKING)
                .inventoryLoadout(inventoryLoadout)
                .minLevel(logToFiremakingLevel(logId))
                .validator(() -> OwnedItems.contains(logId))
                .build();
    }

    private static Branch createBranch(int logId, String activityName, List<WorldTile> firemakingTiles) {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(false)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new GoToFiremakingTileLeaf(firemakingTiles),
                        new LightLogsLeaf(logId)
                )
        );
    }

    private static int logToFiremakingLevel(int logID) {
        switch (logID) {
            case ItemID.LOGS:
                return 1;
            case ItemID.OAK_LOGS:
                return 15;
            case ItemID.WILLOW_LOGS:
                return 30;
            case ItemID.MAPLE_LOGS:
                return 45;
            case ItemID.YEW_LOGS:
                return 60;
            case ItemID.MAGIC_LOGS:
                return 75;
            case ItemID.REDWOOD_LOGS:
                return 90;
        }
        return 0;
    }
}
