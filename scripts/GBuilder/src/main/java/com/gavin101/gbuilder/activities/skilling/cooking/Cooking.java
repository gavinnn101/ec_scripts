package com.gavin101.gbuilder.activities.skilling.cooking;

import com.gavin101.GLib.GLib;
import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.GLib.leafs.common.GoToAreaLeaf;
import com.gavin101.gbuilder.activities.skilling.cooking.constants.CookingConstants;
import com.gavin101.gbuilder.activities.skilling.cooking.leafs.CookFoodLeaf;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.SkillActivity;
import com.gavin101.gbuilder.activitymanager.branches.ValidateActivityBranch;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class Cooking {
    public static SkillActivity createActivity(int rawFoodId) {
        String activityName = String.format("Cooking food: %s", GLib.getItemName(rawFoodId));

        InventoryLoadout inventoryLoadout = new InventoryLoadout()
                .addReq(rawFoodId, () -> Math.min(OwnedItems.count(rawFoodId), 28))
                .setStrict(() -> !Inventory.contains(rawFoodId));

        return SkillActivity.builder()
                .name(activityName)
                .branchSupplier(() -> createBranch(rawFoodId, activityName))
                .activitySkill(Skill.COOKING)
                .inventoryLoadout(inventoryLoadout)
                .minLevel(fishToCookingLevel(rawFoodId))
                .validator(() -> Quest.COOKS_ASSISTANT.isFinished() && OwnedItems.contains(rawFoodId))
                .build();
    }

    private static Branch createBranch(int rawFoodId, String activityName) {
        return new ValidateActivityBranch(
                ActivityManager.getActivity(activityName)).addLeafs(
                GetCurrentActivityLoadoutLeaf.builder()
                        .buyRemainder(false)
                        .build(),
                new IsAfkBranch().addLeafs(
                        new GoToAreaLeaf(CookingConstants.LUMBRIDGE_CASTLE_COOKING_AREA),
                        new CookFoodLeaf("Cooking range", rawFoodId)
                )
        );
    }

    private static int fishToCookingLevel(int rawFoodID) {
        switch (rawFoodID) {
            case ItemID.RAW_SHRIMPS:
            case ItemID.RAW_SARDINE:
            case ItemID.RAW_ANCHOVIES:
                return 1;
            case ItemID.RAW_HERRING:
                return 5;
            case ItemID.RAW_TROUT:
                return 15;
            case ItemID.RAW_PIKE:
                return 20;
            case ItemID.RAW_SALMON:
                return 25;
            case ItemID.RAW_TUNA:
            case ItemID.RAW_KARAMBWAN:
                return 30;
            case ItemID.RAW_LOBSTER:
                return 40;
            case ItemID.RAW_BASS:
                return 43;
            case ItemID.RAW_SWORDFISH:
                return 45;
            case ItemID.RAW_MONKFISH:
                return 62;
            case ItemID.RAW_SHARK:
                return 80;
            case ItemID.RAW_SEA_TURTLE:
                return 82;
            case ItemID.RAW_ANGLERFISH:
                return 84;
            case ItemID.RAW_DARK_CRAB:
                return 90;
            default:
                // Should probably throw exception or something instead..
                return 0;
        }
    }
}
