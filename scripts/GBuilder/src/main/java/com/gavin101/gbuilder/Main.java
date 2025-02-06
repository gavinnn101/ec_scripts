package com.gavin101.gbuilder;

import com.gavin101.GLib.leafs.common.CacheBankLeaf;
import com.gavin101.GLib.leafs.common.EnableRunningLeaf;
import com.gavin101.GLib.leafs.common.RequestMuleLeaf;
import com.gavin101.gbuilder.activities.quests.goblindiplomacy.GoblinDiplomacy;
import com.gavin101.gbuilder.activities.quests.impcatcher.ImpCatcher;
import com.gavin101.gbuilder.activities.quests.restlessghost.RestlessGhost;
import com.gavin101.gbuilder.activities.quests.runemysteries.RuneMysteries;
import com.gavin101.gbuilder.activities.quests.sheepshearer.SheepShearer;
import com.gavin101.gbuilder.activities.quests.witchspotion.WitchsPotion;
import com.gavin101.gbuilder.activities.skilling.cooking.Cooking;
import com.gavin101.gbuilder.activities.skilling.firemaking.Firemaking;
import com.gavin101.gbuilder.activities.skilling.fishing.BaitFishing.BaitFishing;
import com.gavin101.gbuilder.activities.skilling.fishing.flyfishing.FlyFishing;
import com.gavin101.gbuilder.activities.skilling.fishing.shrimp.ShrimpFishing;
import com.gavin101.gbuilder.activities.quests.cooksassistant.CooksAssistant;
import com.gavin101.gbuilder.activities.quests.doricsquest.DoricsQuest;
import com.gavin101.gbuilder.activities.skilling.mining.Mining;
import com.gavin101.gbuilder.activities.skilling.woodcutting.Woodcutting;
import com.gavin101.gbuilder.activitymanager.ActivityManager;
import com.gavin101.gbuilder.activitymanager.leafs.SetActivityLeaf;
import com.gavin101.gbuilder.utility.branches.NeedLoadoutMoneyBranch;
import com.gavin101.gbuilder.utility.constants.Common;
import com.gavin101.gbuilder.utility.leafs.SellItemsLeaf;
import net.eternalclient.api.Client;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.internal.InteractionMode;
import net.eternalclient.api.listeners.Notify;
import net.eternalclient.api.listeners.Painter;
import net.eternalclient.api.listeners.message.ChatMessageEvent;
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
        name = "GBuilder",
        description = "AIO Account builder",
        author = "Gavin101",
        category = ScriptCategory.OTHER,
        version = 1.0
)

public class Main extends AbstractScript implements Painter {
    private Timer timer;
    private Tree tree;

    public static boolean needToChangeFiremakingTile = false;


    public void onStart(String[] args) {
        Log.info("Setting interaction mode to INSTANT_REPLAYED");
        Client.getSettings().setInteractionMode(InteractionMode.INSTANT_REPLAYED);

        timer = new Timer();
        tree = new Tree();

        // Wrapper function to register all activities for the script
        // TODO: Add a way to optionally add certain activities up to different levels and different quests to allow the creation of different account builds.
        registerActivities();

        // List registered activities once on start for debugging
        ActivityManager.listActivities();

        // Create base tree with required branches/leafs.
        tree.addBranches(
                new EnableRunningLeaf(),
                new CacheBankLeaf(),
                new SetActivityLeaf(),
                new NeedLoadoutMoneyBranch().addLeafs(
                        new SellItemsLeaf(Common.itemsToSell),
                        RequestMuleLeaf.builder().build()
                )
        );
        // Add registered task branches to tree.
        for (Branch activityBranch : ActivityManager.getActivityBranches()) {
            tree.addBranches(activityBranch);
        }
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
                add("Current branch: " + Tree.currentBranch);
                add("Current leaf: " + Tree.currentLeaf);
                if (ActivityManager.getCurrentActivity() != null) {
                    add("Current activity: " + ActivityManager.getCurrentActivity().getName());
                    add("Current activity time left: " + ActivityManager.getFormattedTimeLeft());
                }
            }}.toArray(new String[0]));

    @Override
    public void onPaint(Graphics2D g) {
        paint.paint(g);
    }

    private void registerActivities() {
        Log.info("Registering all activities...");
        ActivityManager.registerActivity(ShrimpFishing.ACTIVITY);
        ActivityManager.registerActivity(BaitFishing.ACTIVITY);
        ActivityManager.registerActivity(FlyFishing.ACTIVITY);

        ActivityManager.registerActivity(Cooking.createActivity(ItemID.RAW_SHRIMPS));
        ActivityManager.registerActivity(Cooking.createActivity(ItemID.RAW_SARDINE));
        ActivityManager.registerActivity(Cooking.createActivity(ItemID.RAW_HERRING));
        ActivityManager.registerActivity(Cooking.createActivity(ItemID.RAW_TROUT));
        ActivityManager.registerActivity(Cooking.createActivity(ItemID.RAW_SALMON));

        ActivityManager.registerActivity(Woodcutting.createActivity(Woodcutting.TreeType.TREE));
        ActivityManager.registerActivity(Woodcutting.createActivity(Woodcutting.TreeType.OAK));
        ActivityManager.registerActivity(Woodcutting.createActivity(Woodcutting.TreeType.WILLOW));

        ActivityManager.registerActivity(Firemaking.createActivity(Firemaking.LogType.NORMAL));
        ActivityManager.registerActivity(Firemaking.createActivity(Firemaking.LogType.OAK));
        ActivityManager.registerActivity(Firemaking.createActivity(Firemaking.LogType.WILLOW));

        ActivityManager.registerActivity(Mining.createActivity(Mining.RockType.COPPER));
        ActivityManager.registerActivity(Mining.createActivity(Mining.RockType.IRON));

        ActivityManager.registerActivity(CooksAssistant.ACTIVITY);
        ActivityManager.registerActivity(DoricsQuest.ACTIVITY);
        ActivityManager.registerActivity(SheepShearer.ACTIVITY);
        ActivityManager.registerActivity(ImpCatcher.ACTIVITY);
        ActivityManager.registerActivity(WitchsPotion.ACTIVITY);
        ActivityManager.registerActivity(GoblinDiplomacy.ACTIVITY);
        ActivityManager.registerActivity(RuneMysteries.ACTIVITY);
        ActivityManager.registerActivity(RestlessGhost.ACTIVITY);
    }

    @Notify
    public void notifyChatMessageEvent(ChatMessageEvent event) {
        String msg = event.getMessage();
        if (msg.equals("You can't light a fire here.")) {
            Log.debug("Not on a valid firemaking tile, setting boolean to move to a new tile.");
            needToChangeFiremakingTile = true;
        }
    }
}
