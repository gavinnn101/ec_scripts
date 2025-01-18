package com.gavin101.accbuilder.branches.tiers;

import com.gavin101.accbuilder.constants.combat.BarbarianCombat;
import com.gavin101.accbuilder.utility.General;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class TierThreeBranch extends Branch {
    @Override
    public boolean isValid() {
        return !Quest.WITCHS_POTION.isFinished()
                || !Quest.GOBLIN_DIPLOMACY.isFinished()
                || Skills.getRealLevel(Skill.WOODCUTTING) < 41
                || Skills.getRealLevel(Skill.MINING) < 41
                || Skills.getRealLevel(Skill.FISHING) < 40
                || OwnedItems.contains(ItemID.RAW_SALMON)
                || OwnedItems.contains(ItemID.RAW_TROUT)
                || OwnedItems.contains(ItemID.WILLOW_LOGS)
                || (General.canStartCombatTier(BarbarianCombat.BARBARIAN_LEVEL_RANGES)
                && (Skills.getRealLevel(Skill.ATTACK) < 40 || Skills.getRealLevel(Skill.STRENGTH) < 40 || Skills.getRealLevel(Skill.DEFENCE) < 40)
        );
    }
}
