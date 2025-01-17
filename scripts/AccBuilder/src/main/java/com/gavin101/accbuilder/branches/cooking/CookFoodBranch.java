package com.gavin101.accbuilder.branches.cooking;

import com.gavin101.accbuilder.Main;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

@RequiredArgsConstructor
public class CookFoodBranch extends Branch {
    private final int rawFoodID;
    private final int tierFishingLevelGoal; // We should only ever cook food after we're finished fishing.

    @Override
    public boolean isValid() {
        if (Quest.COOKS_ASSISTANT.isFinished()
                && Skills.getRealLevel(Skill.COOKING) >= fishToCookingLevel(rawFoodID)
                && Skills.getRealLevel(Skill.FISHING) >= tierFishingLevelGoal
                && OwnedItems.contains(rawFoodID)
        ) {
            Main.setActivity("Cooking food", Skill.COOKING, 0);
            return true;
        }
        return false;
    }

    public int fishToCookingLevel(int rawFoodID) {
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
