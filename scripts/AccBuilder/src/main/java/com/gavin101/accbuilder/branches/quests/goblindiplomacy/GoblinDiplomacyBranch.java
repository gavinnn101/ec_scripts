package com.gavin101.accbuilder.branches.quests.goblindiplomacy;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.combat.CowCombat;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class GoblinDiplomacyBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.GOBLIN_DIPLOMACY.isFinished()
                && Quest.SHEEP_SHEARER.isFinished()
                && Quest.DORICS_QUEST.isFinished()
                && Quest.COOKS_ASSISTANT.isFinished()
                && Quest.IMP_CATCHER.isFinished()
                && !OwnedItems.contains(i -> i.containsName("Raw"))
                && Skills.getRealLevel(Skill.ATTACK) >= CowCombat.COW_LEVEL_GOALS.get(Skill.ATTACK)
                && Skills.getRealLevel(Skill.STRENGTH) >= CowCombat.COW_LEVEL_GOALS.get(Skill.STRENGTH)
                && Skills.getRealLevel(Skill.DEFENCE) >= CowCombat.COW_LEVEL_GOALS.get(Skill.DEFENCE)
                && !Players.localPlayer().isMoving()
                && !Players.localPlayer().isAnimating()) {
            Main.setActivity("Doing quest: Goblin Diplomacy");
            return true;
        }
        return false;
    }
}
