package com.gavin101.accbuilder.branches.tiers;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.combat.BarbarianCombat;
import com.gavin101.accbuilder.constants.combat.CowCombat;
import com.gavin101.accbuilder.utility.General;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class TierTwoBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.SHEEP_SHEARER.isFinished()
                || !Quest.IMP_CATCHER.isFinished()
                || Skills.getRealLevel(Skill.WOODCUTTING) < 31
                || Skills.getRealLevel(Skill.MINING) < 31
                || Skills.getRealLevel(Skill.FISHING) < 20
                || OwnedItems.contains(ItemID.RAW_SARDINE)
                || OwnedItems.contains(ItemID.RAW_HERRING)
                || OwnedItems.contains(ItemID.OAK_LOGS)
                || (General.canStartCombatTier(CowCombat.COW_LEVEL_RANGES) && !General.canStartCombatTier(BarbarianCombat.BARBARIAN_LEVEL_RANGES)
        )) {
            Main.currentTier = "Tier two";
            return true;
        } else {
            return false;
        }
    }
}
