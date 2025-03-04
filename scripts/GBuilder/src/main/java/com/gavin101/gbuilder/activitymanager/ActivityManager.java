package com.gavin101.gbuilder.activitymanager;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.activitymanager.activity.Activity;
import com.gavin101.gbuilder.activitymanager.activity.SkillActivity;
import lombok.Getter;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.listeners.skill.SkillTracker;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.skill.Skill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ActivityManager {
    private static final List<Activity> registeredActivities = new ArrayList<>();
    @Getter
    private static final List<Branch> activityBranches = new ArrayList<>();

    @Getter
    private static final Timer activityTimer = new Timer();
    @Getter
    private static Activity currentActivity;

    public static void registerActivity(Activity activity) {
//        Log.info("Registering activity: " +activity.getName());
        registeredActivities.add(activity);
        activityBranches.add(activity.getBranch());
    }

    public static void deregisterActivity(Activity activity) {
        Log.info("Deregistering activity: " +activity.getName());
        registeredActivities.remove(activity);
        activityBranches.remove(activity.getBranch());
    }

    public static boolean currentActivityIsValid() {
        // Current activity is valid if it's not null, validator is true, and elapsed time is less than the max duration.
        if (currentActivity == null) {
            Log.info("Current activity is null.");
            return false;
        }
        if (!currentActivity.isValid()) {
            Log.info("Current activity validator is no longer true.");
            return false;
        } else if (getCurrentActivityTimeLeftMs() <= 0) {
            Log.info("Current activity timer is over the max duration.");
            return false;
        }
        return true;
    }

    public static void setCurrentActivity() {
        Log.info("Looking for a random valid activity to start.");
        Collections.shuffle(registeredActivities);
        for (Activity activity : registeredActivities) {
            if (activity.isValid()) {
                Log.info("Setting current activity to: " + activity.getName());
                currentActivity = activity;
                // We want a new max duration each time a new activity activates
                int randomDuration = Calculations.random(30, 60);
                activity.setMaxDurationMinutes(randomDuration);
                Log.info("Current activity max duration (minutes): " +randomDuration);
                // Reset our time tracking
                activityTimer.reset();
                if (currentActivity.getType().equals(Activity.ActivityType.SKILL)) {
                    // Reset exp gained for the next activity so we get accurate xp/hr.
                    SkillActivity currentSkillActivity = (SkillActivity) currentActivity;
                    Skill currentSkill = currentSkillActivity.getActivitySkill();
                    SkillTracker.deregister();
                    SkillTracker.start(currentSkill);
                }
                break;
            }
        }
    }

    public static long getCurrentActivityTimeLeftMs() {
        if (currentActivity == null) {
            return 0;
        }
        long maxDurationMs = GLib.minutesToMs(currentActivity.getMaxDurationMinutes());
        return maxDurationMs - activityTimer.elapsed();
    }

    private static String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long hours = seconds / 3600;
        seconds = seconds % 3600;
        long minutes = seconds / 60;
        seconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String getFormattedTimeLeft() {
        return formatTime(getCurrentActivityTimeLeftMs());
    }

    public static void listActivities() {
        Log.info("Listing registered activities:");
        for (Activity activity : registeredActivities) {
            Log.info(activity.getDetailedString());
        }
    }

    public static Activity getActivity(String activityName) {
//        Log.debug("Looking for activity: " +activityName +" in the list of registered activities.");
        for (Activity activity : registeredActivities) {
            if (activity.getName().equals(activityName)) {
//                Log.debug("Found matching activity in list.");
                return activity;
            }
        }
        Log.debug("Couldn't find matching activity, returning null.");
        return null;
    }
}