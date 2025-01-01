package com.gavin101.amethystaio;

import net.eternalclient.api.utilities.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GActivityHelper {
    private static Object currentActivity = null;
    private static final Map<Object, ActivityState> activityStates = new HashMap<>();

    public static class ActivityState {
        public boolean needToBuyItems = true;
        public boolean needToWithdrawItems = true;
        public Supplier<Boolean> finishedCondition;
        public Map<Integer, Integer> requiredItems;

        public ActivityState(Map<Integer, Integer> requiredItems, Supplier<Boolean> finishedCondition) {
            this.requiredItems = new HashMap<>(requiredItems);
            this.finishedCondition = finishedCondition;
        }
    }

    public static boolean startNewActivity(Object newActivity, Map<Integer, Integer> requiredItems, Supplier<Boolean> finishedCondition) {
        if (newActivity != null && !newActivity.equals(currentActivity)) {
            Log.debug("Starting new activity: " + newActivity);
            if (finishedCondition.get()) {
                Log.debug("Activity " + newActivity + " is already finished, skipping.");
                return false;
            }
            currentActivity = newActivity;
            ActivityState newState = new ActivityState(requiredItems, finishedCondition);
            activityStates.put(newActivity, newState);
            Log.debug("Created new state for activity: " + newActivity + ", needToBuyItems: " + newState.needToBuyItems + ", needToWithdrawItems: " + newState.needToWithdrawItems);
            return true;
        }
        return false;
    }

    public static boolean isCurrentActivity(Object activity) {
        boolean result = (currentActivity != null && currentActivity.equals(activity));
        Log.debug("Checking if " + activity + " is current activity: " + result);
        return result;
    }

    public static boolean needToBuyItems() {
        ActivityState state = activityStates.get(currentActivity);
        boolean result = (state != null && state.needToBuyItems);
        Log.debug("Checking needToBuyItems for " + currentActivity + ": " + result);
        return result;
    }

    public static void setNeedToBuyItems(boolean value) {
        ActivityState state = activityStates.get(currentActivity);
        if (state != null) {
            state.needToBuyItems = value;
            Log.debug("Set needToBuyItems to " + value + " for activity: " + currentActivity);
        } else {
            Log.debug("Failed to set needToBuyItems: No state for activity " + currentActivity);
        }
    }

    public static boolean needToWithdrawItems() {
        ActivityState state = activityStates.get(currentActivity);
        boolean result = (state != null && state.needToWithdrawItems);
        Log.debug("Checking needToWithdrawItems for " + currentActivity + ": " + result);
        return result;
    }

    public static void setNeedToWithdrawItems(boolean value) {
        ActivityState state = activityStates.get(currentActivity);
        if (state != null) {
            state.needToWithdrawItems = value;
            Log.debug("Set needToWithdrawItems to " + value + " for activity: " + currentActivity);
        } else {
            Log.debug("Failed to set needToWithdrawItems: No state for activity " + currentActivity);
        }
    }

    public static boolean isActivityFinished() {
        ActivityState state = activityStates.get(currentActivity);
        if (state == null) {
            Log.debug("Checking if activity is finished: " + currentActivity + " (null state)");
            return true;
        } else {
            boolean finished = state.finishedCondition.get();
            Log.debug("Checking if activity is finished: " + currentActivity + " - " + finished);
            if (finished) {
                resetCurrentActivity();
            }
            return finished;
        }
    }

    private static void resetCurrentActivity() {
        Log.debug("Resetting current activity: " + currentActivity);
        currentActivity = null;
    }

    public static Object getCurrentActivity() {
        Log.debug("Getting current activity: " + currentActivity);
        return currentActivity;
    }
}