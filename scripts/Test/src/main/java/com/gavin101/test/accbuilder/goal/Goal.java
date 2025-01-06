package com.gavin101.test.accbuilder.goal;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Data
@SuperBuilder
public abstract class Goal {
    private final String goalName;
    private final Supplier<Boolean> completedCondition;
    @Builder.Default
    private List<Supplier<Boolean>> startRequirements = new ArrayList<>();

    public boolean hasStartRequirements() {
        return startRequirements == null
                || startRequirements.isEmpty()
                || startRequirements.stream().allMatch(Supplier::get);
    }

    public boolean isCompleted() {
        return completedCondition.get();
    }

    public void addStartRequirement(Supplier<Boolean> startRequirement) {
        this.startRequirements.add(startRequirement);
    }
}