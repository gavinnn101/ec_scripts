package com.gavin101.accbuilder.branches.tiers;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.combat.FleshCrawlersCombat;
import com.gavin101.accbuilder.utility.General;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class TierFourBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.RUNE_MYSTERIES.isFinished()
                || !Quest.THE_RESTLESS_GHOST.isFinished()
                || Skills.getRealLevel(Skill.WOODCUTTING) < 51
                || Skills.getRealLevel(Skill.FIREMAKING) < 50
                || Skills.getRealLevel(Skill.FISHING) < 50
                || Skills.getRealLevel(Skill.COOKING) < 50
                || Skills.getRealLevel(Skill.MINING) < 51
                || (General.canStartCombatTier(FleshCrawlersCombat.FLESH_CRAWLERS_LEVEL_RANGES)
                && (Skills.getRealLevel(Skill.ATTACK) < 50 || Skills.getRealLevel(Skill.STRENGTH) < 50 || Skills.getRealLevel(Skill.DEFENCE) < 50)
        )) {
            Main.currentTier = "Tier four";
            return true;
        } else {
            return false;
        }
    }
}
