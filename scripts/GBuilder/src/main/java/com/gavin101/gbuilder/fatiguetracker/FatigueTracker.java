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

    private static final long[] EARLY_BREAK_DURATION = {3, 15};
    private static final long[] MID_BREAK_DURATION = {8, 20};
    private static final long[] LATE_BREAK_DURATION = {10, 25};
    private static final long[] EXTENDED_BREAK_DURATION = {18, 30};
    private static final long[] SLEEP_BREAK_DURATION = {360, 540};

    private static final long[] EARLY_BREAK_INTERVAL = {40, 100};
    private static final long[] MID_BREAK_INTERVAL = {30, 90};
    private static final long[] LATE_BREAK_INTERVAL = {30, 75};
    private static final long[] EXTENDED_BREAK_INTERVAL = {30, 60};

    private static long currentBreakDuration = 0;  // Current break duration in minutes
    private static long nextBreakDuration = 0;     // Next break duration in minutes
    private static long nextBreakInterval = 0;     // Time until next break in minutes
    private static long targetSessionLength = 480 + (long) Calculations.random(0, 240);

    private static boolean isSleepBreak;

    public static int getCurrentReactionTime() {
        long timePlayedMinutes = GLib.msToMinutes(timer.elapsed());

        double predictableChance;
        double normalChance;
        double afkChance;

        if (timePlayedMinutes >= 0 && timePlayedMinutes <= 120) {         // 0-2 hours
            predictableChance = 0.40;
            normalChance = 0.50;
            afkChance = 0.10;
        } else if (timePlayedMinutes >= 120 && timePlayedMinutes <= 240) { // 2-4 hours
            predictableChance = 0.30;
            normalChance = 0.50;
            afkChance = 0.20;
        } else if (timePlayedMinutes >= 240 && timePlayedMinutes <= 360) { // 4-6 hours
            predictableChance = 0.20;
            normalChance = 0.50;
            afkChance = 0.30;
        } else {                                                           // 6+ hours
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
        timer.reset();
        breakTimer.reset();
        targetSessionLength = 480 + (long) Calculations.random(0, 240);
        isSleepBreak = false;
        nextBreakInterval = 0;
        nextBreakDuration = 0;
        currentBreakDuration = 0;
    }

    public static boolean shouldTakeBreak() {
        if (onBreak) return false;

        long timePlayedMinutes = GLib.msToMinutes(timer.elapsed());
        long timeSinceLastBreak = GLib.msToMinutes(breakTimer.elapsed());

        // Only calculate next break interval if we haven't yet
        if (nextBreakInterval == 0) {
            // Check if we've reached our target session length
            if (timePlayedMinutes >= targetSessionLength) {
                nextBreakDuration = SLEEP_BREAK_DURATION[0] + Math.round(Calculations.random(0, SLEEP_BREAK_DURATION[1] - SLEEP_BREAK_DURATION[0]));
                isSleepBreak = true;
                Log.info("Session length reached (" + timePlayedMinutes + " minutes), initiating sleep break");
                return true;
            }

            // Normal break calculations
            long[] intervalRange;
            if (timePlayedMinutes <= 120) {
                intervalRange = EARLY_BREAK_INTERVAL;
            } else if (timePlayedMinutes <= 240) {
                intervalRange = MID_BREAK_INTERVAL;
            } else if (timePlayedMinutes <= 360) {
                intervalRange = LATE_BREAK_INTERVAL;
            } else {
                intervalRange = EXTENDED_BREAK_INTERVAL;
            }

            nextBreakInterval = intervalRange[0] + Math.round(Calculations.random(0, intervalRange[1] - intervalRange[0]));
            Log.info("Next break in: " +getFormattedNextBreakIn());

            // Calculate next break duration
            long[] durationRange;
            if (timePlayedMinutes <= 120) {
                durationRange = EARLY_BREAK_DURATION;
            } else if (timePlayedMinutes <= 240) {
                durationRange = MID_BREAK_DURATION;
            } else if (timePlayedMinutes <= 360) {
                durationRange = LATE_BREAK_DURATION;
            } else {
                durationRange = EXTENDED_BREAK_DURATION;
            }
            nextBreakDuration = durationRange[0] + Math.round(Calculations.random(0, durationRange[1] - durationRange[0]));
            Log.info("Next break duration: " +getFormattedNextBreakDuration());
            isSleepBreak = false;
        }

        return timeSinceLastBreak >= nextBreakInterval;
    }

    public static void startBreak() {
        onBreak = true;
        currentBreakDuration = nextBreakDuration;
        nextBreakInterval = 0; // Reset so new interval will be calculated after break
        breakTimer.reset();
    }

    public static boolean isBreakFinished() {
        if (!onBreak) return true;

        long currentBreakMinutes = GLib.msToMinutes(breakTimer.elapsed());

        if (currentBreakMinutes >= currentBreakDuration) {
            onBreak = false;
            breakTimer.reset();
            if (!isSleepBreak) {
                nextBreakInterval = 0;
                nextBreakDuration = 0;
                currentBreakDuration = 0;
            } else {
                // If sleep break, start a new session
                startNewSession();
            }
            return true;
        }
        return false;
    }

    public static String getFormattedRemainingBreakTime() {
        if (!onBreak) return "00:00";

        long remainingMs = currentBreakDuration * 60000 - breakTimer.elapsed();
        return formatTime(Math.max(0, remainingMs));
    }

    public static String getFormattedNextBreakIn() {
        if (onBreak) return "00:00";

        // Use the cached nextBreakInterval
        long remainingMs = nextBreakInterval * 60000 - breakTimer.elapsed();
        return formatTime(Math.max(0, remainingMs));
    }

    public static String getFormattedNextBreakDuration() {
        return formatTime(nextBreakDuration * 60000);
    }

    private static String formatTime(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String getFormattedRemainingSessionTime() {
        if (isSleepBreak) return "00:00";

        long timePlayedMinutes = GLib.msToMinutes(timer.elapsed());
        long remainingMinutes = Math.max(0, targetSessionLength - timePlayedMinutes);
        return formatTime(remainingMinutes * 60000);  // Convert to ms for formatTime
    }
}