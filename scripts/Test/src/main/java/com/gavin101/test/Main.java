package com.gavin101.test;


import com.gavin101.test.accbuilder.AccBuilder;
import com.gavin101.test.accbuilder.AvailableGoals;
import net.eternalclient.api.Client;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.internal.InteractionMode;
import net.eternalclient.api.listeners.Painter;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.script.ScriptCategory;
import net.eternalclient.api.script.ScriptManifest;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.paint.CustomPaint;

import java.awt.*;
import java.text.NumberFormat;

@ScriptManifest(
        name = "Test",
        description = "test script",
        author = "Gavin101",
        category = ScriptCategory.OTHER,
        version = 1.0
)

public class Main extends AbstractScript implements Painter {
    public static AccBuilder accBuilder = new AccBuilder();
    private Tree goalTree;
    private CustomPaint paint;

    public void onStart(String[] args) {
        Log.info("Setting interaction mode to INSTANT_REPLAYED");
        Client.getSettings().setInteractionMode(InteractionMode.INSTANT_REPLAYED);

        int goalFishingLevel = 20;
        Main.accBuilder
                .addGoal(AvailableGoals.Quests.DORICS_QUEST)
                .addGoal(AvailableGoals.Skills.FISHING(goalFishingLevel));
        goalTree = Main.accBuilder.buildGoalsTree();
        Log.debug("Tree branches: " +goalTree.getBranches());
        paint = accBuilder.buildPaint();
    }

    @Override
    public int onLoop() {
        return goalTree.onLoop();
    }

    @Override
    public void onPaint(Graphics2D g) {
        paint.paint(g);
    }
}
