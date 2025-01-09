package com.gavin101.accbuilder.branches.combat.barbarians;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.combat.BarbarianCombat;
import com.gavin101.accbuilder.utility.General;
import com.gavin101.accbuilder.utility.LevelRange;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class FightBarbariansBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!General.canStartCombatTier(BarbarianCombat.BARBARIAN_LEVEL_RANGES)) {
            return false;
        }

        for (Map.Entry<Skill, LevelRange> entry : BarbarianCombat.BARBARIAN_LEVEL_RANGES.entrySet()) {
            Skill skill = entry.getKey();
            LevelRange range = entry.getValue();
            int currentLevel = Skills.getRealLevel(skill);

            if (currentLevel < range.getMax()) {
                Main.setActivity("Training " + skill + " on barbarians", skill, range.getMax());
                Main.attackStyle = GLib.SKILL_TO_ATTACK_STYLE.get(skill);
                return true;
            }
        }
        return false;
    }
}
