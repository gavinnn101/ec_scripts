package com.gavin101.test.accbuilder;

import com.gavin101.test.accbuilder.goal.QuestGoal;
import com.gavin101.test.accbuilder.goal.SkillGoal;
import com.gavin101.test.accbuilder.goal.branches.ValidateGoalBranch;
import com.gavin101.test.accbuilder.goal.Goal;
import com.gavin101.test.accbuilder.goal.leafs.SetCurrentGoalLeaf;
import com.gavin101.test.accbuilder.quests.doricsquest.DoricsQuestBranch;
import com.gavin101.test.accbuilder.tiers.fishing.FishingTier;
import com.gavin101.test.accbuilder.tiers.fishing.FishingTiers;
import com.gavin101.test.leafs.TestLeaf;
import lombok.Data;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.listeners.skill.SkillTracker;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.paint.CustomPaint;
import net.eternalclient.api.utilities.paint.PaintLocations;
import net.eternalclient.api.wrappers.skill.Skill;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import static net.eternalclient.api.script.AbstractScript.getScriptName;
import static net.eternalclient.api.script.AbstractScript.getScriptVersion;

@Data
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
        List<Branch> allTaskBranches = new ArrayList<>();

        // Collect all task branches
        for (Goal goal : goals) {
            Log.info("Getting branches for goal: " + goal.getGoalName());
            List<Branch> branches = getGoalBranches(goal);
            if (!branches.isEmpty()) {
                // Each branch should already have ValidateGoalBranch and SetCurrentGoalLeaf
                allTaskBranches.addAll(branches);
            }
        }

        if (!allTaskBranches.isEmpty()) {
            Collections.shuffle(allTaskBranches);

            // Log the task order
            Log.info("Task order after shuffle:");
            for (Branch branch : allTaskBranches) {
                if (branch instanceof ValidateGoalBranch) {
                    ValidateGoalBranch validateBranch = (ValidateGoalBranch) branch;
                    Log.info("- " + validateBranch.getGoal().getGoalName());
                }
            }

            for (Branch taskBranch : allTaskBranches) {
                goalsTree.addBranches(taskBranch);
            }
        }

        return goalsTree;
    }

    private List<Branch> getGoalBranches(Goal goal) {
        List<Branch> goalBranches = new ArrayList<>();

        if (goal instanceof SkillGoal) {
            Log.info("Getting skillgoalbranches for goal: " +goal.getGoalName());
            return getSkillGoalBranches((SkillGoal) goal);
        } else if (goal instanceof QuestGoal) {
            return getQuestGoalBranches((QuestGoal) goal);
        }

        return goalBranches;
    }

    private List<Branch> getSkillGoalBranches(SkillGoal goal) {
        List<Branch> branches = new ArrayList<>();
        int currentLevel = Skills.getRealLevel(goal.getSkill());
        int targetLevel = goal.getTargetLevel();

        if (goal.getSkill() == Skill.FISHING) {
            List<FishingTier> applicableTiers = FishingTiers.getAllTiers().stream()
                    .filter(tier -> currentLevel <= tier.getMaxLevel() && tier.getMinLevel() <= targetLevel)
                    .collect(Collectors.toList());

            for (FishingTier tier : applicableTiers) {
                Branch tierBranch = tier.buildTierBranch();
                if (tierBranch != null) {
                    // Create a sub-goal for this tier
                    SkillGoal tierGoal = SkillGoal.builder()
                            .goalName("Get Fishing to level " + tier.getMaxLevel())
                            .skill(Skill.FISHING)
                            .targetLevel(tier.getMaxLevel())
                            .completedCondition(() -> Skills.getRealLevel(Skill.FISHING) >= tier.getMaxLevel())
                            .build();

                    branches.add(new ValidateGoalBranch(tierGoal).addLeafs(
                            new SetCurrentGoalLeaf(tierGoal),
                            tierBranch
                    ));
                }
            }
        }
        return branches;
    }

    private List<Branch> getQuestGoalBranches(QuestGoal goal) {
        List<Branch> branches = new ArrayList<>();

        switch(goal.getQuest()) {
            case DORICS_QUEST:
                branches.add(new DoricsQuestBranch().getBranch(goal));
                break;
        }
        return branches;
    }

    public void listGoals() {
        Log.info("Listing goals being used...");
        for (Goal goal : goals) {
            Log.info("--------------");
            Log.info("Goal: " +goal.getGoalName());
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
                    add("Current goal: " +currentGoal.getGoalName());
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