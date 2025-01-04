package com.gavin101.accbuilder.branches.quests.impcatcher;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.combat.ChickenCombat;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class ImpCatcherBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.IMP_CATCHER.isFinished()
                && Quest.DORICS_QUEST.isFinished()
                && Quest.COOKS_ASSISTANT.isFinished()
                && !OwnedItems.contains(i -> i.containsName("Raw"))
                && Skills.getRealLevel(Skill.ATTACK) >= ChickenCombat.CHICKEN_LEVEL_GOALS.get(Skill.ATTACK)
                && Skills.getRealLevel(Skill.STRENGTH) >= ChickenCombat.CHICKEN_LEVEL_GOALS.get(Skill.STRENGTH)
                && Skills.getRealLevel(Skill.DEFENCE) >= ChickenCombat.CHICKEN_LEVEL_GOALS.get(Skill.DEFENCE)
                && !Players.localPlayer().isMoving()
                && !Players.localPlayer().isAnimating()) {
            Main.setActivity("Doing quest: Imp Catcher");
            return true;
        }
        return false;
    }
}
