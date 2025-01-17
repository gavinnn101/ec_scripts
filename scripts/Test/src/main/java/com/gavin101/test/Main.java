package com.gavin101.test;


import com.gavin101.test.branches.TestBranch;
import com.gavin101.test.leafs.TestLeaf;
import net.eternalclient.api.Client;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.internal.InteractionMode;
import net.eternalclient.api.listeners.Painter;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.script.ScriptCategory;
import net.eternalclient.api.script.ScriptManifest;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.paint.CustomPaint;
import net.eternalclient.api.utilities.paint.PaintLocations;

import java.awt.*;
import java.util.ArrayList;

@ScriptManifest(
        name = "Test",
        description = "test script",
        author = "Gavin101",
        category = ScriptCategory.OTHER,
        version = 1.0
)

public class Main extends AbstractScript implements Painter {
    private final Tree tree = new Tree();
    private Timer startTimer;

    public void onStart(String[] args) {
        Log.info("Setting interaction mode to INSTANT_REPLAYED");
        Client.getSettings().setInteractionMode(InteractionMode.INSTANT_REPLAYED);

        startTimer = new Timer();

        tree.addBranches(
                new TestBranch().addLeafs(
                        new TestLeaf()
                )
        );
    }

    @Override
    public int onLoop() {
        return tree.onLoop();
    }


    private final CustomPaint paint = new CustomPaint(PaintLocations.TOP_LEFT_PLAY_SCREEN)
            .setInfoSupplier(() -> new ArrayList<String>()
            {{
                add(getScriptName() + " v" + getScriptVersion());
                add("Runtime: " + startTimer);
                add("Current Branch: " + Tree.currentBranch);
                add("Current Leaf: " + Tree.currentLeaf);
            }}.toArray(new String[0]));

    @Override
    public void onPaint(Graphics2D g) {
        paint.paint(g);
    }
}
