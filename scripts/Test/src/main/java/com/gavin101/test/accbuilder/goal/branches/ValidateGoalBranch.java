package com.gavin101.test.accbuilder.goal.branches;

import com.gavin101.test.accbuilder.goal.Goal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.frameworks.tree.Branch;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class ValidateGoalBranch extends Branch {
    private final Goal goal;

    @Override
    public boolean isValid() {
        return !goal.isCompleted()
                && goal.hasStartRequirements();
    }
}
