package com.gavin101.test.accbuilder;

import com.gavin101.test.accbuilder.branches.ValidateGoalBranch;
import com.gavin101.test.accbuilder.leafs.SetCurrentGoalLeaf;
import com.gavin101.test.leafs.TestLeaf;
import lombok.Getter;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.listeners.skill.SkillTracker;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.paint.CustomPaint;
import net.eternalclient.api.utilities.paint.PaintLocations;
import net.eternalclient.api.wrappers.skill.Skill;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static net.eternalclient.api.script.AbstractScript.getScriptName;
import static net.eternalclient.api.script.AbstractScript.getScriptVersion;

@Getter
public class AccBuilder {
    public final List<Goal> goals = new ArrayList<>();

    public static Goal currentGoal;

    public Timer startTimer;

    public AccBuilder addGoal(Goal goal) {
        this.goals.add(goal);
        return this;
    }

    public Tree buildGoalsTree() {
        Log.info("Building goals tree based on goals below:");
        listGoals();
        Tree goalsTree = new Tree();

        for (Goal goal : goals) {
            Branch goalTaskList = buildGoalTaskList(goal);
            goalsTree.addBranches(goalTaskList);
        }
        return goalsTree;
    }

    public Branch buildGoalTaskList(Goal goal) {
        Log.debug("Building task list for goal: " +goal.getGoalDescription());
        return new ValidateGoalBranch(goal).addLeafs(
                new SetCurrentGoalLeaf(goal),
                new TestLeaf(goal)
        );
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

    public CustomPaint buildPaint() {
        startTimer = new Timer();
        return new CustomPaint(PaintLocations.TOP_LEFT_PLAY_SCREEN)
                .setInfoSupplier(() -> new ArrayList<String>()
                {{
                    add(getScriptName() + " v" + getScriptVersion());
                    add("Runtime: " + startTimer);
                    add("Current goal: " +currentGoal.getGoalDescription());
                    add("Current Branch: " + Tree.currentBranch);
                    add("Current Leaf: " + Tree.currentLeaf);
                }}.toArray(new String[0]));
    }

    private long calculateExpPerHour(Skill skill) {
        long timeRan = startTimer.elapsed();
        long expGained = SkillTracker.getGainedExperience(skill);

        if (timeRan == 0 || expGained == 0) return 0;

        double hoursElapsed = timeRan / 3600000.0;
        double expPerHour = expGained / hoursElapsed;

        return Math.round(expPerHour);
    }

    private String formatExpPerHour(Skill skill) {
        return NumberFormat.getInstance().format(calculateExpPerHour(skill));
    }
}