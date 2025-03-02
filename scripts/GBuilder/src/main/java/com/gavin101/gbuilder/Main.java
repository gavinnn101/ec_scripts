package com.gavin101.gbuilder;

import com.gavin101.GLib.GLib;
import com.gavin101.GLib.leafs.common.CacheBankLeaf;
import com.gavin101.GLib.leafs.common.CloseChatLeaf;
import com.gavin101.GLib.leafs.common.EnableRunningLeaf;
import com.gavin101.GLib.leafs.common.RequestMuleLeaf;
import com.gavin101.gbuilder.activities.misc.tanning.Tanning;
import com.gavin101.gbuilder.activities.quests.goblindiplomacy.GoblinDiplomacy;
import com.gavin101.gbuilder.activities.quests.impcatcher.ImpCatcher;
import com.gavin101.gbuilder.activities.quests.restlessghost.RestlessGhost;
import com.gavin101.gbuilder.activities.quests.runemysteries.RuneMysteries;
import com.gavin101.gbuilder.activities.quests.sheepshearer.SheepShearer;
import com.gavin101.gbuilder.activities.quests.witchspotion.WitchsPotion;
import com.gavin101.gbuilder.activities.skilling.combat.Combat;
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
import com.gavin101.gbuilder.activitymanager.activity.Activity;
import com.gavin101.gbuilder.activitymanager.activity.SkillActivity;
import com.gavin101.gbuilder.activitymanager.leafs.SetActivityLeaf;
import com.gavin101.gbuilder.breaks.BreakFinishedLeaf;
import com.gavin101.gbuilder.breaks.LoggedOutLeaf;
import com.gavin101.gbuilder.breaks.TakeBreakLeaf;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import com.gavin101.gbuilder.utility.branches.NeedLoadoutMoneyBranch;
import com.gavin101.gbuilder.utility.constants.Common;
import com.gavin101.gbuilder.utility.leafs.EndScriptLeaf;
import com.gavin101.gbuilder.utility.leafs.HandleDeathLeaf;
import com.gavin101.gbuilder.utility.leafs.SellItemsLeaf;
import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.events.random.RandomManager;
import net.eternalclient.api.events.random.events.LoginEvent;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.internal.InteractionMode;
import net.eternalclient.api.listeners.Notify;
import net.eternalclient.api.listeners.Painter;
import net.eternalclient.api.listeners.message.ChatMessageEvent;
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

    public static boolean died = false;

    public void onStart(String[] args) {
        Log.info("Setting interaction mode to INSTANT_REPLAYED");
        Client.getSettings().setInteractionMode(InteractionMode.INSTANT_REPLAYED);

        // We'll check skill levels too early if we don't wait until we're logged in.
        GLib.waitUntilLoggedIn();

        GLib.hideRoofs();

        // TODO: Set rendering and fps limit as script args with defaults.
        GLib.disableRendering();
        GLib.setFpsLimit(3);

        timer = new Timer();
        tree = new Tree();

        // Wrapper function to register all activities for the script
        // TODO: Add a way to optionally add certain activities up to different levels and different quests to allow the creation of different account builds.
        registerActivities();

        // List registered activities once on start for debugging
        ActivityManager.listActivities();

        // Create base tree with required branches/leafs.
        tree.addBranches(
                new EndScriptLeaf(),
//                new TakeBreakLeaf(),
//                new BreakFinishedLeaf(),
//                new LoggedOutLeaf(),
                new CloseChatLeaf(),
                new EnableRunningLeaf(),
                new HandleDeathLeaf(),
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
                    Activity currentActivity = ActivityManager.getCurrentActivity();
                    add("Current activity: " + currentActivity.getName());
                    if (currentActivity.getType().equals(Activity.ActivityType.SKILL)) {
                        SkillActivity currentSkillActivity = (SkillActivity) currentActivity;
                        Skill currentSkill = currentSkillActivity.getActivitySkill();
                        add("Current activity skill: " +currentSkill);
                        add("Current activity skill level: " +Skills.getRealLevel(currentSkillActivity.getActivitySkill()));
                        add("Current activity skill xp/hr: " +calculateExpPerHour(currentSkill));
                    }
                    add("Current activity time left: " + ActivityManager.getFormattedTimeLeft());
                }
                if (FatigueTracker.isOnBreak()) {
                    add("Current break duration: " +FatigueTracker.getFormattedRemainingBreakTime());
                } else {
                    add("Next break in: " +FatigueTracker.getFormattedNextBreakIn());
                    add("Next break duration: " +FatigueTracker.getFormattedNextBreakDuration());
                }
            }}.toArray(new String[0]));

    @Override
    public void onPaint(Graphics2D g) {
        paint.paint(g);
    }

    private long calculateExpPerHour(Skill skill) {
        long timeRan = ActivityManager.getActivityTimer().elapsed();
        long expGained = SkillTracker.getGainedExperience(skill);

        if (timeRan == 0 || expGained == 0) {
            return 0;
        }

        double hoursElapsed = timeRan / 3600000.0;
        double expPerHour = expGained / hoursElapsed;

        return Math.round(expPerHour);
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
        ActivityManager.registerActivity(Mining.createActivity(Mining.RockType.TIN));
        ActivityManager.registerActivity(Mining.createActivity(Mining.RockType.IRON));

        ActivityManager.registerActivity(CooksAssistant.ACTIVITY);
        ActivityManager.registerActivity(DoricsQuest.ACTIVITY);
        ActivityManager.registerActivity(SheepShearer.ACTIVITY);
        ActivityManager.registerActivity(ImpCatcher.ACTIVITY);
        ActivityManager.registerActivity(WitchsPotion.ACTIVITY);
        ActivityManager.registerActivity(GoblinDiplomacy.ACTIVITY);
        ActivityManager.registerActivity(RuneMysteries.ACTIVITY);
        ActivityManager.registerActivity(RestlessGhost.ACTIVITY);

        Combat.MonsterTier.CHICKENS.register();
        Combat.MonsterTier.COWS.register();
        Combat.MonsterTier.BARBARIANS.register();
        Combat.MonsterTier.ALKHARIDWARRIORS.register();
        Combat.MonsterTier.FLESHCRAWLERS.register();

        ActivityManager.registerActivity(Tanning.createActivity(Tanning.LeatherType.LEATHER));
    }

    @Notify
    public void notifyChatMessageEvent(ChatMessageEvent event) {
        String msg = event.getMessage();
        if (msg.equals("You can't light a fire here.")) {
            Log.debug("Not on a valid firemaking tile, setting boolean to move to a new tile.");
            needToChangeFiremakingTile = true;
        } else if (msg.equals("Some of your dropped items are being held in a gravestone, near where you died.")) {
            died = true;
        } else if (msg.contains("Your gravestone has expired") || msg.contains("You successfully retrieved everything from your gravestone")) {
            Log.info("We collected our items or our gravestone has expired, setting `died = false`.");
            died = false;
        }
    }
}