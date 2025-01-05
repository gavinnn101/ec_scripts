package com.gavin101.test.accbuilder;

import lombok.Getter;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.utilities.Log;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AccBuilder {
    public final List<Goal> goals = new ArrayList<>();

    private Goal currentGoal;

    public AccBuilder addGoal(Goal goal) {
        this.goals.add(goal);
        return this;
    }

    public Tree buildGoalsTree() {
        Log.info("Building goals tree");
        listGoals();
        Tree goalsTree = new Tree();

        goalsTree.addBranches();

        return goalsTree;
    }

    public void listGoals() {
        Log.info("Listing goals being used...");
        for (Goal goal : goals) {
            Log.info("--------------");
            Log.info("Goal: " +goal.getGoalDescription());
            Log.info("Is the goal completed?: " +goal.isCompleted());
            Log.info("Do we have goal start requirements?: " +goal.hasStartRequirements());
        }
        Log.info("--------------");
    }
}