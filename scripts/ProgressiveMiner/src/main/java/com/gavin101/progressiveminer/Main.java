package com.gavin101.progressiveminer;

import com.gavin101.breakhelper.branches.TakeBreakBranch;
import com.gavin101.breakhelper.leafs.TakeBreakLeaf;
import com.gavin101.progressiveminer.branches.MineCopperBranch;
import com.gavin101.progressiveminer.branches.MineIronBranch;
import com.gavin101.progressiveminer.leafs.DropOresLeaf;
import com.gavin101.progressiveminer.leafs.MineRockLeaf;
import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
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
import net.eternalclient.api.wrappers.skill.Skill;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;

@ScriptManifest(
        name = "Progressive Miner",
        description = "it mines, progressively.",
        author = "Gavin101",
        category = ScriptCategory.MINING,
        version = 1.0
)

public class Main extends AbstractScript implements Painter {
    private final Tree tree = new Tree();
    public static Timer startTimer;
    private final NumberFormat nf = NumberFormat.getInstance();

    public void onStart(String[] args) {
        Log.info("Setting interaction mode to INSTANT_REPLAYED");
        Client.getSettings().setInteractionMode(InteractionMode.INSTANT_REPLAYED);

        startTimer = new Timer();
        SkillTracker.start(Skill.MINING);

        tree.addBranches(
                new TakeBreakBranch().addLeafs(
                        new TakeBreakLeaf()
                ),
                new MineCopperBranch().addLeafs(
                        new DropOresLeaf(),
                        new MineRockLeaf("Copper rocks")),
                new MineIronBranch().addLeafs(
                        new DropOresLeaf(),
                        new MineRockLeaf("Iron rocks"))
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
                add("Mining level: " + Skills.getRealLevel(Skill.MINING));
                add("Mining exp/hr: " +nf.format(calculateExpPerHour()));
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
