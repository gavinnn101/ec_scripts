package com.gavin101.accbuilder.branches.tiers;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.combat.ChickenCombat;
import com.gavin101.accbuilder.constants.combat.CowCombat;
import com.gavin101.accbuilder.utility.General;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class TierOneBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.COOKS_ASSISTANT.isFinished()
                || !Quest.DORICS_QUEST.isFinished()
                || Skills.getRealLevel(Skill.WOODCUTTING) < 15
                || Skills.getRealLevel(Skill.MINING) < 15
                || Skills.getRealLevel(Skill.FISHING) < 5
                || OwnedItems.contains(ItemID.RAW_SHRIMPS)
                || OwnedItems.contains(ItemID.LOGS)
                || (General.canStartCombatTier(ChickenCombat.CHICKEN_LEVEL_RANGES) && !General.canStartCombatTier(CowCombat.COW_LEVEL_RANGES)
        )) {
            Main.currentTier = "Tier one";
            return true;
        } else {
            return false;
        }
    }
}
