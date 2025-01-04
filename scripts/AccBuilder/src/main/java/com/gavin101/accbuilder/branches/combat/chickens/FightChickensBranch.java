package com.gavin101.accbuilder.branches.combat.chickens;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

import static com.gavin101.accbuilder.constants.combat.ChickenCombat.CHICKEN_LEVEL_GOALS;


public class FightChickensBranch extends Branch {

    @Override
    public boolean isValid() {
        if (Players.localPlayer().isMoving()) {
            return false;
        }

        if (OwnedItems.contains(i -> i.containsName("Raw"))) {
            return false;
        }

        if (Skills.getRealLevel(Skill.FISHING) < 5) {
            return false;
        }

        for (Map.Entry<Skill, Integer> entry : CHICKEN_LEVEL_GOALS.entrySet()) {
            Skill skill = entry.getKey();
            int goalLevel = entry.getValue();
            if (Skills.getRealLevel(skill) < goalLevel) {
                Main.setActivity("Training " +skill +" on chickens", skill, goalLevel);
                Main.attackStyle = GLib.SKILL_TO_ATTACK_STYLE.get(skill);
                return true;
            }
        }
        return false;
    }
}
