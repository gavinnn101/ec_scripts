package com.gavin101.accbuilder.branches.combat.cows;

import com.gavin101.GLib.GLib;
import com.gavin101.accbuilder.Main;
import com.gavin101.accbuilder.constants.fishing.BaitFishing;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.Map;

import static com.gavin101.accbuilder.constants.combat.CowCombat.COW_LEVEL_GOALS;


public class FightCowsBranch extends Branch {

    @Override
    public boolean isValid() {
        if (Players.localPlayer().isMoving()) {
            return false;
        }

        if (OwnedItems.contains(i -> i.containsName("Raw"))) {
            return false;
        }

        if (Skills.getRealLevel(Skill.FISHING) < BaitFishing.BAIT_FISHING_LEVEL_GOAL) {
            return false;
        }

        for (Map.Entry<Skill, Integer> entry : COW_LEVEL_GOALS.entrySet()) {
            Skill skill = entry.getKey();
            int goalLevel = entry.getValue();
            if (Skills.getRealLevel(skill) < goalLevel) {
                Main.setActivity("Training " +skill +" on cows", skill, goalLevel);
                Main.attackStyle = GLib.SKILL_TO_ATTACK_STYLE.get(skill);
                return true;
            }
        }
        return false;
    }
}
