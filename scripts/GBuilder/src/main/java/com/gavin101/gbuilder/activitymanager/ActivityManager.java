package com.gavin101.gbuilder.activitymanager;

import com.gavin101.GLib.GLib;
import com.gavin101.gbuilder.activitymanager.activity.Activity;
import lombok.Getter;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ActivityManager {
    private static final List<Activity> activities = new ArrayList<>();

    @Getter
    private static final Timer activityTimer = new Timer();
    @Getter
    private static Activity currentActivity;

    public static void registerActivity(Activity activity) {
        activities.add(activity);
    }

    public static boolean currentActivityIsValid() {
        // Current activity is valid if it's not null, validator is true, and elapsed time is less than the max duration.
        if (currentActivity == null) {
            Log.info("Current activity is null.");
            return false;
        }
        if (!currentActivity.getValidator().get()) {
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
        Collections.shuffle(activities);
        for (Activity activity : activities) {
            if (activity.isValid()) {
                Log.info("Setting current activity to: " +activity.getName());
                Log.info("Current activity max duration (minutes): " +activity.getMaxDurationMinutes());
                currentActivity = activity;
                activityTimer.reset();
                break;
            }
        }
    }

    public static long getCurrentActivityTimeLeftMs() {
        long maxDurationMs = GLib.minutesToMs(currentActivity.getMaxDurationMinutes());
        return maxDurationMs - activityTimer.elapsed();
    }

    public static String getFormattedTimeLeft() {
        long milliseconds = getCurrentActivityTimeLeftMs();
        long seconds = milliseconds / 1000;
        long hours = seconds / 3600;
        seconds = seconds % 3600;
        long minutes = seconds / 60;
        seconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void listActivities() {
        Log.info("Listing registered activities:");
        for (Activity activity : activities) {
            Log.info(activity.getDetailedString());
        }
    }
}
