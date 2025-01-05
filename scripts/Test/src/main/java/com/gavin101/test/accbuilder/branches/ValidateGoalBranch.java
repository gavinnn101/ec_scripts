package com.gavin101.test.accbuilder.branches;

import com.gavin101.test.accbuilder.Goal;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.frameworks.tree.Branch;

@RequiredArgsConstructor
public class ValidateGoalBranch extends Branch {
    private final Goal goal;

    @Override
    public boolean isValid() {
        return !goal.isCompleted()
                && goal.hasStartRequirements();
    }
}
