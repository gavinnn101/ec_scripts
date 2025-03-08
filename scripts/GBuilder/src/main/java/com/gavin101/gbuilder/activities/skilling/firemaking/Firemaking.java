package com.gavin101.gbuilder.activities.skilling.firemaking;

import com.gavin101.GLib.GLib;
import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.GLib.leafs.common.GoToAreaLeaf;
import com.gavin101.gbuilder.activities.skilling.firemaking.constants.FiremakingConstants;
import com.gavin101.gbuilder.activities.skilling.firemaking.leafs.GoToFiremakingTileLeaf;
import com.gavin101.gbuilder.activities.skilling.firemaking.leafs.LightLogsLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.SkillActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.skill.Skill;


public class Firemaking {
    @Getter
    @RequiredArgsConstructor
    public enum LogType {
        NORMAL(GLib.getItemName(ItemID.LOGS), ItemID.LOGS, 1, 15),
        OAK(GLib.getItemName(ItemID.OAK_LOGS), ItemID.OAK_LOGS, 15, 30),
        WILLOW(GLib.getItemName(ItemID.WILLOW_LOGS), ItemID.WILLOW_LOGS, 30, 50),
        MAPLE(GLib.getItemName(ItemID.MAPLE_LOGS), ItemID.MAPLE_LOGS, 45, 60),
        YEW(GLib.getItemName(ItemID.YEW_LOGS), ItemID.YEW_LOGS, 60, 75),
        MAGIC(GLib.getItemName(ItemID.MAGIC_LOGS), ItemID.MAGIC_LOGS, 75, 90),
        REDWOOD(GLib.getItemName(ItemID.REDWOOD_LOGS), ItemID.REDWOOD_LOGS, 90, 99);

        private final String name;
        private final int itemId;
        private final int minLevel;
        private final int maxLevel;
    }

    public static SkillActivity createActivity(LogType logType) {
        String activityName = String.format("Firemaking %s", logType.getName());

        FiremakingConstants.FiremakingArea firemakingArea = getRandomFiremakingArea();

        int maxLevel;
        if (logType.equals(LogType.WILLOW)) {
            maxLevel = logType.getMaxLevel() + Calculations.random(-5, 10);
        } else {
            maxLevel = logType.getMaxLevel();
        }

        InventoryLoadout inventoryLoadout = new InventoryLoadout()
                .addReq(ItemID.TINDERBOX)
                .addReq(logType.getItemId(), () -> Math.min(OwnedItems.count(logType.getItemId()), Inventory.getSize() - 1))
                .setStrict(() -> !Inventory.contains(logType.getItemId()));

        return SkillActivity.builder()
                .name(activityName)
                .branchSupplier(() -> createBranch(logType, activityName, firemakingArea))
                .activitySkill(Skill.FIREMAKING)
                .inventoryLoadout(inventoryLoadout)
                .minLevel(logType.getMinLevel())
                .maxLevel(maxLevel)
                .validator(() -> OwnedItems.contains(logType.getItemId()))
                .build();
    }

    private static Branch createBranch(LogType logType, String activityName, FiremakingConstants.FiremakingArea firemakingArea) {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(false)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new GoToAreaLeaf(firemakingArea.getArea()),
                        new GoToFiremakingTileLeaf(firemakingArea),
                        new LightLogsLeaf(logType.getItemId())
                )
        );
    }

    public static FiremakingConstants.FiremakingArea getRandomFiremakingArea() {
        FiremakingConstants.FiremakingArea[] areas = FiremakingConstants.FiremakingArea.values();
        int randomIndex = Calculations.random(0, areas.length);
        return areas[randomIndex];
    }

    public static boolean atValidFiremakingTile() {
        WorldTile playerTile = Players.localPlayer().getWorldTile();
        GameObject playerTileFire = GameObjects.closest(i -> i.hasName("Fire") && i.getWorldTile().equals(playerTile));
        return playerTileFire == null;
    }
}