package com.gavin101.accbuilder.leafs.common;

import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.events.LogoutEvent;
import net.eternalclient.api.events.random.RandomManager;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.skill.Skill;

public class EndScriptLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Skills.getRealLevel(Skill.ATTACK) >= 30
                && Skills.getRealLevel(Skill.STRENGTH) >= 30
                && Skills.getRealLevel(Skill.DEFENCE) >= 30
                && Skills.getRealLevel(Skill.FISHING) >= 20;
    }

    @Override
    public int onLoop() {
        if (Client.isLoggedIn()) {
            Log.info("Logging out and disabling auto login.");
            RandomManager.setAutoLoginEnabled(false);
            new LogoutEvent().setEventCompleteCondition(
                    () -> !Client.isLoggedIn(), Calculations.random(500, 2500)
            ).execute();
        }
        Log.info("Stopping script.");
        AbstractScript.setStopped();
        return ReactionGenerator.getNormal();
    }
}
