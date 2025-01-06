package com.gavin101.test.accbuilder.goal.leafs;

import com.gavin101.test.accbuilder.AccBuilder;
import com.gavin101.test.accbuilder.goal.Goal;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.frameworks.tree.Leaf;

@RequiredArgsConstructor
public class SetCurrentGoalLeaf extends Leaf {
    private final Goal currentGoal;

    @Override
    public boolean isValid() {
        return AccBuilder.currentGoal == null || !AccBuilder.currentGoal.equals(currentGoal);
    }

    @Override
    public int onLoop() {
        AccBuilder.currentGoal = currentGoal;
        return 0;
    }
}
