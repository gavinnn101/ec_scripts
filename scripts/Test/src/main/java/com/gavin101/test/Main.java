package com.gavin101.test;


import com.gavin101.test.accbuilder.AccBuilder;
import com.gavin101.test.accbuilder.AvailableGoals;
import com.gavin101.test.accbuilder.Goal;
import com.gavin101.test.branches.TestBranch;
import com.gavin101.test.leafs.TestLeaf;
import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.internal.InteractionMode;
import net.eternalclient.api.listeners.Painter;
import net.eternalclient.api.listeners.skill.SkillTracker;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.script.ScriptCategory;
import net.eternalclient.api.script.ScriptManifest;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.paint.CustomPaint;
import net.eternalclient.api.utilities.paint.PaintLocations;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;

@ScriptManifest(
        name = "Test",
        description = "test script",
        author = "Gavin101",
        category = ScriptCategory.OTHER,
        version = 1.0
)

public class Main extends AbstractScript implements Painter {
    public static Timer startTimer;
    private final NumberFormat nf = NumberFormat.getInstance();

    public static AccBuilder accBuilder = new AccBuilder();
    private Tree goalTree;

    public void onStart(String[] args) {
        Log.info("Setting interaction mode to INSTANT_REPLAYED");
        Client.getSettings().setInteractionMode(InteractionMode.INSTANT_REPLAYED);

        startTimer = new Timer();

        int goalFishingLevel = 20;
        Main.accBuilder
                .addGoal(AvailableGoals.Quests.DORICS_QUEST)
                .addGoal(AvailableGoals.Skills.FISHING(goalFishingLevel));
        goalTree = Main.accBuilder.buildGoalsTree();
    }

    @Override
    public int onLoop() {
        return goalTree.onLoop();
    }

    private final CustomPaint paint = new CustomPaint(PaintLocations.TOP_LEFT_PLAY_SCREEN)
            .setInfoSupplier(() -> new ArrayList<String>()
            {{
                add(getScriptName() + " v" + getScriptVersion());
                add("Runtime: " + startTimer);
                add("Current Branch: " + Tree.currentBranch);
                add("Current Leaf: " + Tree.currentLeaf);
                add("Current goal: " +accBuilder.getCurrentGoal());
            }}.toArray(new String[0]));

    @Override
    public void onPaint(Graphics2D g) {
        paint.paint(g);
    }

    private long calculateExpPerHour() {
        long timeRan = startTimer.elapsed();
        long expGained = SkillTracker.getGainedExperience(Skill.MINING);

        if (timeRan == 0 || expGained == 0) return 0;

        double hoursElapsed = timeRan / 3600000.0;
        double expPerHour = expGained / hoursElapsed;

        return Math.round(expPerHour);
    }
}
