package com.gavin101.accbuilder.branches.tiers;

import com.gavin101.accbuilder.constants.combat.FleshCrawlersCombat;
import com.gavin101.accbuilder.utility.General;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class TierFourBranch extends Branch {
    @Override
    public boolean isValid() {
        return !Quest.RUNE_MYSTERIES.isFinished()
                || !Quest.THE_RESTLESS_GHOST.isFinished()
                || Skills.getRealLevel(Skill.WOODCUTTING) < 51
                || Skills.getRealLevel(Skill.MINING) < 51
                || Skills.getRealLevel(Skill.FISHING) < 50
                || OwnedItems.contains(ItemID.RAW_SALMON)
                || OwnedItems.contains(ItemID.RAW_TROUT)
                || OwnedItems.contains(ItemID.WILLOW_LOGS)
                || (General.canStartCombatTier(FleshCrawlersCombat.FLESH_CRAWLERS_LEVEL_RANGES)
                && (Skills.getRealLevel(Skill.ATTACK) < 50 || Skills.getRealLevel(Skill.STRENGTH) < 50 || Skills.getRealLevel(Skill.DEFENCE) < 50)
        );
    }
}
