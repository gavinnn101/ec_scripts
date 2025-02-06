package com.gavin101.gbuilder.activities.skilling.combat.leafs;

import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.AttackStyle;
import net.eternalclient.api.accessors.Combat;
import net.eternalclient.api.events.combat.SetAttackStyleEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;

@RequiredArgsConstructor
public class SetAttackStyleLeaf extends Leaf {
    private final AttackStyle attackStyle;

    @Override
    public boolean isValid() {
        return !Combat.getAttackStyle().equals(attackStyle);
    }

    @Override
    public int onLoop() {
        Log.info("Setting attack style to: " + attackStyle.getName());
        new SetAttackStyleEvent(attackStyle).setEventCompleteCondition(
                () -> Combat.getAttackStyle().equals(attackStyle),
                Calculations.random(350, 1000)
        ).execute();
        return ReactionGenerator.getNormal();
    }
}
