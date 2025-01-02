package com.gavin101.accbuilder.branches.combat.alkharidguards;

import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.AlkharidGuardsCombat;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

import static com.gavin101.accbuilder.constants.Common.SKILL_TO_ATTACK_STYLE;

public class FightAlkharidGuardsBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Players.localPlayer().isMoving()) {
            return false;
        }

        for (Map.Entry<Skill, Integer> entry : AlkharidGuardsCombat.ALKHARID_GUARD_LEVEL_GOALS.entrySet()) {
            Skill skill = entry.getKey();
            int goalLevel = entry.getValue();
            if (Skills.getRealLevel(skill) < goalLevel) {
                Main.currentSkillToTrain = skill;
                Main.currentLevelGoal = goalLevel;
                Main.attackStyle = SKILL_TO_ATTACK_STYLE.get(skill);
                return true;
            }
        }
        return false;
    }
}
