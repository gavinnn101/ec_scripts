package com.gavin101.test.accbuilder;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Supplier;

@Builder
@Getter
public class Goal {
    private String goalDescription;
    private Supplier<Boolean> completedCondition;
    private List<Supplier<Boolean>> startRequirements;

    public boolean hasStartRequirements() {
        return startRequirements == null
                || startRequirements.isEmpty()
                || startRequirements.stream().allMatch(Supplier::get);
    }

    public boolean isCompleted() {
        return completedCondition.get();
    }
}