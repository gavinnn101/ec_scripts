package com.gavin101.accbuilder.branches.quests.sheepshearer;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class SheepShearerBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.SHEEP_SHEARER.isFinished()
                && Quest.DORICS_QUEST.isFinished()
                && Quest.COOKS_ASSISTANT.isFinished()
                && Quest.IMP_CATCHER.isFinished()
                && Skills.getRealLevel(Skill.FISHING) >= 20
                && !Players.localPlayer().isMoving()
                && !Players.localPlayer().isAnimating()) {
            Main.setActivity("Doing quest: Sheep Shearer");
            return true;
        }
        return false;
    }
}
