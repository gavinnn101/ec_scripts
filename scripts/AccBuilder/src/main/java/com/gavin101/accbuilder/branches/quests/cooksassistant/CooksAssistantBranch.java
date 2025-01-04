package com.gavin101.accbuilder.branches.quests.cooksassistant;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.fishing.ShrimpFishing;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class CooksAssistantBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Skills.getRealLevel(Skill.FISHING) < ShrimpFishing.SHRIMP_FISHING_LEVEL_GOAL) {
            return false;
        }

        if (!Quest.COOKS_ASSISTANT.isFinished()
                && Skills.getRealLevel(Skill.FISHING) >= 5
                && !Players.localPlayer().isMoving()
                && !Players.localPlayer().isAnimating()) {
            Main.setActivity("Doing quest: Cook's Assistant");
            return true;
        }
        return false;
    }
}
