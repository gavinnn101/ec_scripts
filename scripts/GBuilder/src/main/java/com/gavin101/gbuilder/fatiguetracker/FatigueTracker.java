package com.gavin101.gbuilder.fatiguetracker;

import com.gavin101.GLib.GLib;
import lombok.Data;
import lombok.Getter;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.math.Calculations;

@Data
public class FatigueTracker {
    private static Timer timer = new Timer();
    private static Timer breakTimer = new Timer();
    @Getter private static boolean onBreak = false;

    private static final long MINUTE_MS = 60000;
    private static final long HOUR_MS = 3600000;

    private static final long[] EARLY_BREAK_DURATION = {3, 15};
    private static final long[] MID_BREAK_DURATION = {8, 20};
    private static final long[] LATE_BREAK_DURATION = {10, 25};
    private static final long[] EXTENDED_BREAK_DURATION = {18, 30};
    private static final long[] SLEEP_BREAK_DURATION = {360, 540};

    private static final long[] EARLY_BREAK_INTERVAL = {40, 100};
    private static final long[] MID_BREAK_INTERVAL = {30, 90};
    private static final long[] LATE_BREAK_INTERVAL = {30, 75};
    private static final long[] EXTENDED_BREAK_INTERVAL = {30, 60};

    private static long currentBreakDuration = 0;  // Duration in milliseconds
    private static long nextBreakDuration = 0;     // Duration in milliseconds
    private static long nextBreakInterval = 0;     // Duration in milliseconds
    private static long targetSessionLength;        // Length in milliseconds
    private static boolean isSleepBreak;

    static {
        // Initialize target session length (8-12 hours)
        long baseSessionMinutes = 480;
        long randomAdditionalMinutes = (long) Calculations.random(0, 240);
        targetSessionLength = (baseSessionMinutes + randomAdditionalMinutes) * MINUTE_MS;
    }

    public static int getCurrentReactionTime() {
        long timePlayedMinutes = timer.elapsed() / MINUTE_MS;
        return calculateReactionTime(timePlayedMinutes);
    }

    private static int calculateReactionTime(long timePlayedMinutes) {
        ReactionTimeProfile profile = getReactionTimeProfile(timePlayedMinutes);
        double roll = Calculations.random(0.0, 1.0);

        if (roll < profile.predictableChance) {
            Log.debug(String.format("Returning predictable reaction time (roll: %.3f, chance: %.3f)",
                    roll, profile.predictableChance));
            return ReactionGenerator.getPredictable();
        } else if (roll < (profile.predictableChance + profile.normalChance)) {
            Log.debug(String.format("Returning normal reaction time (roll: %.3f, chance: %.3f)",
                    roll, profile.normalChance));
            return ReactionGenerator.getNormal();
        } else {
            Log.debug(String.format("Returning AFK reaction time (roll: %.3f, chance: %.3f)",
                    roll, profile.afkChance));
            return ReactionGenerator.getAFK();
        }
    }

    private static ReactionTimeProfile getReactionTimeProfile(long timePlayedMinutes) {
        if (timePlayedMinutes <= 120) {         // 0-2 hours
            return new ReactionTimeProfile(0.40, 0.50, 0.10);
        } else if (timePlayedMinutes <= 240) {  // 2-4 hours
            return new ReactionTimeProfile(0.30, 0.50, 0.20);
        } else if (timePlayedMinutes <= 360) {  // 4-6 hours
            return new ReactionTimeProfile(0.20, 0.50, 0.30);
        } else {                                // 6+ hours
            return new ReactionTimeProfile(0.10, 0.60, 0.30);
        }
    }

    public static void startNewSession() {
        Log.info("Starting fresh fatigue tracker session.");
        timer.reset();
        breakTimer.reset();
        targetSessionLength = (480 + (long) Calculations.random(0, 240)) * MINUTE_MS;
        isSleepBreak = false;
        nextBreakInterval = 0;
        nextBreakDuration = 0;
        currentBreakDuration = 0;
    }

    public static boolean shouldTakeBreak() {
        if (onBreak) return false;

        long timePlayedMs = timer.elapsed();
        long timeSinceLastBreakMs = breakTimer.elapsed();

        if (nextBreakInterval == 0) {
            calculateNextBreak(timePlayedMs);
        }

        return timeSinceLastBreakMs >= nextBreakInterval;
    }

    private static void calculateNextBreak(long timePlayedMs) {
        long timePlayedMinutes = timePlayedMs / MINUTE_MS;

        // Check for sleep break
        if (timePlayedMs >= targetSessionLength) {
            initiateSleepBreak(timePlayedMinutes);
            return;
        }

        // Calculate normal break
        long[] intervalRange = getIntervalRange(timePlayedMinutes);
        long[] durationRange = getDurationRange(timePlayedMinutes);

        // Calculate random interval and duration with decimal precision
        nextBreakInterval = calculateRandomDuration(intervalRange);
        nextBreakDuration = calculateRandomDuration(durationRange);

        Log.info("Next break in: " + getFormattedNextBreakIn());
        Log.info("Next break duration: " + getFormattedNextBreakDuration());
        isSleepBreak = false;
    }

    private static void initiateSleepBreak(long timePlayedMinutes) {
        nextBreakDuration = calculateRandomDuration(SLEEP_BREAK_DURATION);
        isSleepBreak = true;
        Log.info("Session length reached (" + timePlayedMinutes + " minutes), initiating sleep break");
    }

    private static long calculateRandomDuration(long[] range) {
        double randomValue = range[0] + Calculations.random(0.0, range[1] - range[0]);
        return (long) (randomValue * MINUTE_MS);
    }

    private static long[] getIntervalRange(long timePlayedMinutes) {
        if (timePlayedMinutes <= 120) return EARLY_BREAK_INTERVAL;
        if (timePlayedMinutes <= 240) return MID_BREAK_INTERVAL;
        if (timePlayedMinutes <= 360) return LATE_BREAK_INTERVAL;
        return EXTENDED_BREAK_INTERVAL;
    }

    private static long[] getDurationRange(long timePlayedMinutes) {
        if (timePlayedMinutes <= 120) return EARLY_BREAK_DURATION;
        if (timePlayedMinutes <= 240) return MID_BREAK_DURATION;
        if (timePlayedMinutes <= 360) return LATE_BREAK_DURATION;
        return EXTENDED_BREAK_DURATION;
    }

    public static void startBreak() {
        onBreak = true;
        currentBreakDuration = nextBreakDuration;
        nextBreakInterval = 0;
        breakTimer.reset();
    }

    public static boolean isBreakFinished() {
        if (!onBreak) return true;

        if (breakTimer.elapsed() >= currentBreakDuration) {
            finishBreak();
            return true;
        }
        return false;
    }

    private static void finishBreak() {
        onBreak = false;
        breakTimer.reset();
        if (isSleepBreak) {
            startNewSession();
        } else {
            nextBreakInterval = 0;
            nextBreakDuration = 0;
            currentBreakDuration = 0;
        }
    }

    public static String getFormattedRemainingBreakTime() {
        if (!onBreak) return "00:00";
        return formatTime(Math.max(0, currentBreakDuration - breakTimer.elapsed()));
    }

    public static String getFormattedNextBreakIn() {
        if (onBreak) return "00:00";
        return formatTime(Math.max(0, nextBreakInterval - breakTimer.elapsed()));
    }

    public static String getFormattedNextBreakDuration() {
        return formatTime(nextBreakDuration);
    }

    public static String getFormattedRemainingSessionTime() {
        if (isSleepBreak) return "00:00";
        return formatTime(Math.max(0, targetSessionLength - timer.elapsed()));
    }

    private static String formatTime(long milliseconds) {
        long totalMinutes = milliseconds / MINUTE_MS;

        if (totalMinutes >= 60) {
            // For durations >= 1 hour, show HH:mm
            long hours = totalMinutes / 60;
            long minutes = totalMinutes % 60;
            return String.format("%02d:%02d", hours, minutes);
        } else {
            // For durations < 1 hour, show MM:SS
            long seconds = (milliseconds / 1000) % 60;
            return String.format("%02d:%02d", totalMinutes, seconds);
        }
    }

    private static class ReactionTimeProfile {
        final double predictableChance;
        final double normalChance;
        final double afkChance;

        ReactionTimeProfile(double predictable, double normal, double afk) {
            this.predictableChance = predictable;
            this.normalChance = normal;
            this.afkChance = afk;
        }
    }
}