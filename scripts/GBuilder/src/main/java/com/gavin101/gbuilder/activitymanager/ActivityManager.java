package com.gavin101.gbuilder.activitymanager;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.activitymanager.activity.Activity;
import lombok.Getter;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.listeners.skill.SkillTracker;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.math.Calculations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityManager {
    private static final List<Activity> registeredActivities = new ArrayList<>();
    @Getter
    private static final List<Branch> activityBranches = new ArrayList<>();
    @Getter
    private static Activity currentActivity;

    // Use Timer to track active time.
    @Getter
    private static final Timer activityTimer = new Timer();

    public static void registerActivity(Activity activity) {
        Log.info("Registering activity: " + activity.getName());
        registeredActivities.add(activity);
        activityBranches.add(activity.getBranch());
    }

    public static boolean currentActivityIsValid() {
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
                // Set a new max duration each time a new activity is started.
                activity.setMaxDurationMinutes(Calculations.random(30, 60));
                Log.info("Current activity max duration (minutes): " + activity.getMaxDurationMinutes());
                currentActivity = activity;
                activityTimer.reset();
                // Reset XP tracking for an accurate xp/hr calculation.
                SkillTracker.deregister();
                SkillTracker.start();
                break;
            }
        }
    }

    public static long getCurrentActivityTimeLeftMs() {
        if (currentActivity == null) return 0;
        long maxDurationMs = GLib.minutesToMs(currentActivity.getMaxDurationMinutes());
        long elapsed = activityTimer.elapsed();
        return maxDurationMs - elapsed;
    }

    public static String getFormattedTimeLeft() {
        return formatTime(getCurrentActivityTimeLeftMs());
    }

    public static void pauseActivityTimer() {
        if (!activityTimer.isPaused()) {
            activityTimer.pause();
            Log.debug(String.format("Activity paused. Total active time: %s", formatTime(activityTimer.elapsed())));
        }
    }

    public static void resumeActivityTimer() {
        if (activityTimer.isPaused()) {
            activityTimer.resume();
            Log.debug(String.format("Activity resumed. Current total time: %s", formatTime(activityTimer.elapsed())));
        }
    }

    private static String formatTime(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void listActivities() {
        Log.info("Listing registered activities:");
        for (Activity activity : registeredActivities) {
            Log.info(activity.getDetailedString());
        }
    }

    public static Activity getActivity(String activityName) {
        Log.debug("Looking for activity: " + activityName + " in the list of registered activities.");
        for (Activity activity : registeredActivities) {
            if (activity.getName().equals(activityName)) {
                Log.debug("Found matching activity in list.");
                return activity;
            }
        }
        Log.debug("Couldn't find matching activity, returning null.");
        return null;
    }
}
