package com.gavin101.gbuilder;

import com.gavin101.GLib.GLib;
import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.GLib.leafs.common.CacheBankLeaf;
import com.gavin101.GLib.leafs.common.EnableRunningLeaf;
import com.gavin101.GLib.leafs.common.GoToAreaLeaf;
import com.gavin101.GLib.leafs.common.RequestMuleLeaf;
import com.gavin101.gbuilder.activities.fishing.common.constants.Fishing;
import com.gavin101.gbuilder.activities.fishing.common.leafs.StartFishingLeaf;
import com.gavin101.gbuilder.activities.fishing.flyfishing.FlyFishingBranch;
import com.gavin101.gbuilder.activities.fishing.flyfishing.FlyFishingConstants;
import com.gavin101.gbuilder.activities.fishing.shrimp.FishShrimpBranch;
import com.gavin101.gbuilder.activities.fishing.shrimp.ShrimpFishingConstants;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.activity.SkillActivity;
import com.gavin101.gbuilder.activitymanager.leafs.GetCurrentActivityLoadoutLeaf;
import com.gavin101.gbuilder.activitymanager.leafs.SetActivityLeaf;
import com.gavin101.gbuilder.utility.branches.HandleFinancesBranch;
import com.gavin101.gbuilder.utility.constants.Common;
import com.gavin101.gbuilder.utility.leafs.SellItemsLeaf;
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
import net.eternalclient.api.wrappers.skill.Skill;

import java.awt.*;
import java.util.ArrayList;

@ScriptManifest(
        name = "GBuilder",
        description = "AIO Account builder",
        author = "Gavin101",
        category = ScriptCategory.OTHER,
        version = 1.0
)

public class Main extends AbstractScript implements Painter {
    private Timer timer;
    private Tree tree;


    public void onStart(String[] args) {
        Log.info("Setting interaction mode to INSTANT_REPLAYED");
        Client.getSettings().setInteractionMode(InteractionMode.INSTANT_REPLAYED);

        timer = new Timer();
        tree = new Tree();

        // Wrapper function to create and register all activities for the script
        registerActivities();
        // List registered activities once on start for debugging
        ActivityManager.listActivities();

        tree.addBranches(
                new EnableRunningLeaf(),
                new CacheBankLeaf(),
                new SetActivityLeaf(),
                new HandleFinancesBranch().addLeafs(
                        new SellItemsLeaf(Common.itemsToSell),
                        RequestMuleLeaf.builder().build()
                ),
                new FishShrimpBranch().addLeafs(
                        GetCurrentActivityLoadoutLeaf.builder()
                                .buyRemainder(true)
                                .build(),
                        new IsAfkBranch().addLeafs(
                                new GoToAreaLeaf(GLib.getRandomArea(Fishing.FISHING_TYPE_TO_AREAS_MAP(Fishing.FishingType.SMALL_FISHING_NET))),
                                new StartFishingLeaf(Fishing.FishingType.SMALL_FISHING_NET)
                        )
                ),
                new FlyFishingBranch().addLeafs(
                        GetCurrentActivityLoadoutLeaf.builder()
                                .buyRemainder(true)
                                .build(),
                        new IsAfkBranch().addLeafs(
                                new GoToAreaLeaf(GLib.getRandomArea(Fishing.FISHING_TYPE_TO_AREAS_MAP(Fishing.FishingType.FLY_FISHING))),
                                new StartFishingLeaf(Fishing.FishingType.FLY_FISHING)
                        )
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
                add("Runtime: " + timer);
                add("Current Branch: " + Tree.currentBranch);
                add("Current Leaf: " + Tree.currentLeaf);
                add("Current Activity: " + ActivityManager.getCurrentActivity().getName());
                add("Current Activity time left: " + ActivityManager.getFormattedTimeLeft());
            }}.toArray(new String[0]));

    @Override
    public void onPaint(Graphics2D g) {
        paint.paint(g);
    }

    private void registerActivities() {
        SkillActivity fishShrimpActivity = SkillActivity.builder()
                .name("Fish shrimp")
                .activitySkill(Skill.FISHING)
                .inventoryLoadout(ShrimpFishingConstants.SHRIMP_INVENTORY)
                .minLevel(1)
                .maxLevel(99)
                .build();
        ActivityManager.registerActivity(fishShrimpActivity);

        SkillActivity flyFishingActivity = SkillActivity.builder()
                .name("Fly fishing")
                .activitySkill(Skill.FISHING)
                .inventoryLoadout(FlyFishingConstants.FLY_FISHING_INVENTORY)
                .minLevel(20)
                .maxLevel(99)
                .build();
        ActivityManager.registerActivity(flyFishingActivity);
    }
}
