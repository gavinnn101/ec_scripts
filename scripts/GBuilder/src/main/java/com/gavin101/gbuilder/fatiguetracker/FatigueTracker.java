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
    private static Timer sessionTimer = new Timer();
    private static Timer breakTimer = new Timer();
    @Getter private static boolean onBreak = false;

    // Define break durations and intervals in minutes (as ranges)
    private static final long[] EARLY_BREAK_DURATION_MIN = {3, 15};
    private static final long[] MID_BREAK_DURATION_MIN   = {8, 20};
    private static final long[] LATE_BREAK_DURATION_MIN  = {10, 25};
    private static final long[] EXTENDED_BREAK_DURATION_MIN = {18, 30};
    private static final long[] SLEEP_BREAK_DURATION_MIN    = {360, 540};

    private static final long[] EARLY_BREAK_INTERVAL_MIN = {40, 100};
    private static final long[] MID_BREAK_INTERVAL_MIN   = {30, 90};
    private static final long[] LATE_BREAK_INTERVAL_MIN  = {30, 75};
    private static final long[] EXTENDED_BREAK_INTERVAL_MIN = {30, 60};

    // Store all durations in milliseconds
    private static long currentBreakDurationMs = 0;
    private static long nextBreakDurationMs = 0;
    private static long nextBreakIntervalMs = 0;
    private static long targetSessionLengthMs = GLib.minutesToMs(480 + (long) Calculations.random(0, 240));

    private static boolean isSleepBreak;

    public static int getCurrentReactionTime() {
        // Get the elapsed session time in ms then convert to minutes
        long timePlayedMinutes = GLib.msToMinutes(sessionTimer.elapsed());
        double predictableChance, normalChance, afkChance;

        if (timePlayedMinutes <= 120) {         // 0-2 hours
            predictableChance = 0.40;
            normalChance = 0.50;
            afkChance = 0.10;
        } else if (timePlayedMinutes <= 240) {    // 2-4 hours
            predictableChance = 0.30;
            normalChance = 0.50;
            afkChance = 0.20;
        } else if (timePlayedMinutes <= 360) {    // 4-6 hours
            predictableChance = 0.20;
            normalChance = 0.50;
            afkChance = 0.30;
        } else {                                // 6+ hours
            predictableChance = 0.10;
            normalChance = 0.60;
            afkChance = 0.30;
        }

        double roll = Calculations.random(0.0, 1.0);
        if (roll < predictableChance) {
            Log.debug(String.format("Returning predictable reaction time (roll: %.3f, chance: %.3f)", roll, predictableChance));
            return ReactionGenerator.getPredictable();
        } else if (roll < (predictableChance + normalChance)) {
            Log.debug(String.format("Returning normal reaction time (roll: %.3f, chance: %.3f)", roll, normalChance));
            return ReactionGenerator.getNormal();
        } else {
            Log.debug(String.format("Returning AFK reaction time (roll: %.3f, chance: %.3f)", roll, afkChance));
            return ReactionGenerator.getAFK();
        }
    }

    public static void startNewSession() {
        Log.info("Starting fresh fatigue tracker session.");
        sessionTimer.reset();
        breakTimer.reset();
        targetSessionLengthMs = GLib.minutesToMs(480 + (long) Calculations.random(0, 240));
        isSleepBreak = false;
        nextBreakIntervalMs = 0;
        nextBreakDurationMs = 0;
        currentBreakDurationMs = 0;
    }

    public static boolean shouldTakeBreak() {
        if (onBreak) return false;

        long timePlayedMs = sessionTimer.elapsed();
        long timePlayedMinutes = GLib.msToMinutes(timePlayedMs);
        long timeSinceLastBreakMs = breakTimer.elapsed();

        // Always check if the session length is reached
        if (timePlayedMs >= targetSessionLengthMs) {
            // Calculate sleep break duration in minutes then convert to ms
            long durationMin = SLEEP_BREAK_DURATION_MIN[0] +
                    Math.round(Calculations.random(0, SLEEP_BREAK_DURATION_MIN[1] - SLEEP_BREAK_DURATION_MIN[0]));
            nextBreakDurationMs = GLib.minutesToMs(durationMin);
            isSleepBreak = true;
            Log.info("Session length reached (" + timePlayedMinutes + " minutes), initiating sleep break");
            return true;
        }

        // If the break interval hasn't been set, calculate it
        if (nextBreakIntervalMs == 0) {
            long[] intervalRange = getIntervalRange(timePlayedMinutes);
            long intervalMin = intervalRange[0] +
                    Math.round(Calculations.random(0, intervalRange[1] - intervalRange[0]));
            nextBreakIntervalMs = GLib.minutesToMs(intervalMin);
            Log.info("Next break in: " + getFormattedNextBreakIn());

            long[] durationRange = getDurationRange(timePlayedMinutes);
            long durationMin = durationRange[0] +
                    Math.round(Calculations.random(0, durationRange[1] - durationRange[0]));
            nextBreakDurationMs = GLib.minutesToMs(durationMin);
            Log.info("Next break duration: " + getFormattedNextBreakDuration());
            isSleepBreak = false;
        }

        return timeSinceLastBreakMs >= nextBreakIntervalMs;
    }

    private static long[] getIntervalRange(long timePlayedMinutes) {
        if (timePlayedMinutes <= 120) {
            return EARLY_BREAK_INTERVAL_MIN;
        } else if (timePlayedMinutes <= 240) {
            return MID_BREAK_INTERVAL_MIN;
        } else if (timePlayedMinutes <= 360) {
            return LATE_BREAK_INTERVAL_MIN;
        } else {
            return EXTENDED_BREAK_INTERVAL_MIN;
        }
    }

    private static long[] getDurationRange(long timePlayedMinutes) {
        if (timePlayedMinutes <= 120) {
            return EARLY_BREAK_DURATION_MIN;
        } else if (timePlayedMinutes <= 240) {
            return MID_BREAK_DURATION_MIN;
        } else if (timePlayedMinutes <= 360) {
            return LATE_BREAK_DURATION_MIN;
        } else {
            return EXTENDED_BREAK_DURATION_MIN;
        }
    }

    public static void startBreak() {
        onBreak = true;
        currentBreakDurationMs = nextBreakDurationMs;
        nextBreakIntervalMs = 0; // Reset so a new interval will be calculated after the break
        breakTimer.reset();
    }

    public static boolean isBreakFinished() {
        if (!onBreak) return true;

        if (breakTimer.elapsed() >= currentBreakDurationMs) {
            onBreak = false;
            breakTimer.reset();
            if (!isSleepBreak) {
                nextBreakIntervalMs = 0;
                nextBreakDurationMs = 0;
                currentBreakDurationMs = 0;
            } else {
                // If it's a sleep break, start a new session
                startNewSession();
            }
            return true;
        }
        return false;
    }

    public static String getFormattedRemainingBreakTime() {
        if (!onBreak) return "00:00";
        long remainingMs = Math.max(0, currentBreakDurationMs - breakTimer.elapsed());
        return formatTime(remainingMs);
    }

    public static String getFormattedNextBreakIn() {
        if (onBreak) return "00:00";
        long remainingMs = Math.max(0, nextBreakIntervalMs - breakTimer.elapsed());
        return formatTime(remainingMs);
    }

    public static String getFormattedNextBreakDuration() {
        return formatTime(nextBreakDurationMs);
    }

    private static String formatTime(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String getFormattedRemainingSessionTime() {
        if (isSleepBreak) return "00:00";
        long remainingMs = Math.max(0, targetSessionLengthMs - sessionTimer.elapsed());
        return formatTime(remainingMs);
    }
}
