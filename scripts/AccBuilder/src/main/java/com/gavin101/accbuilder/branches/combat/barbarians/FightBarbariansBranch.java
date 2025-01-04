package com.gavin101.accbuilder.branches.combat.barbarians;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.combat.BarbarianCombat;
import com.gavin101.accbuilder.constants.fishing.FlyFishing;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

public class FightBarbariansBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Players.localPlayer().isMoving()) {
            return false;
        }

        if (OwnedItems.contains(i -> i.containsName("Raw"))) {
            return false;
        }

        if (Skills.getRealLevel(Skill.FISHING) < FlyFishing.FLY_FISHING_LEVEL_GOAL) {
            return false;
        }

        for (Map.Entry<Skill, Integer> entry : BarbarianCombat.BARBARIAN_LEVEL_GOALS.entrySet()) {
            Skill skill = entry.getKey();
            int goalLevel = entry.getValue();
            if (Skills.getRealLevel(skill) < goalLevel) {
                Main.setActivity("Training " +skill +" on barbarians", skill, goalLevel);
                Main.attackStyle = GLib.SKILL_TO_ATTACK_STYLE.get(skill);
                return true;
            }
        }
        return false;
    }
}
