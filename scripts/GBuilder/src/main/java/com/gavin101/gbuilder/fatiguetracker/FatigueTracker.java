package com.gavin101.gbuilder.fatiguetracker;

import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.Getter;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.math.Calculations;

@Data
public class FatigueTracker {
    private static final int MAX_FATIGUE = 100;
    private static final int FATIGUE_INCREMENT = 1;
    private static final long FATIGUE_INCREMENT_INTERVAL = TimeUnit.MINUTES.toMillis(5);

    // Session time thresholds
    private static final long EARLY_SESSION = TimeUnit.HOURS.toMillis(2);
    private static final long MID_SESSION = TimeUnit.HOURS.toMillis(4);
    private static final long LATE_SESSION = TimeUnit.HOURS.toMillis(6);
    private static final long END_SESSION = TimeUnit.HOURS.toMillis(8);

    @Getter private static int fatigueLevel = 0;
    private static Timer fatigueTimer = new Timer();
    private static Timer breakTimer = new Timer();
    private static Timer nextBreakTimer = new Timer();
    private static Timer sessionTimer = new Timer();

    @Getter private static boolean onBreak = false;
    @Getter private static long currentBreakDuration = 0;
    @Getter private static long nextBreakIn = 0;
    @Getter private static long nextBreakDuration = 0;

    // Calculate next break based on session time and fatigue
    private static void calculateNextBreak() {
        long sessionTime = sessionTimer.elapsed();
        long minTime, maxTime;

        // Determine break interval based on session time
        if (sessionTime < EARLY_SESSION) {
            // Early session: 45-75 minutes
            minTime = TimeUnit.MINUTES.toMillis(45);
            maxTime = TimeUnit.MINUTES.toMillis(75);
        } else if (sessionTime < MID_SESSION) {
            // Mid session: 30-45 minutes
            minTime = TimeUnit.MINUTES.toMillis(30);
            maxTime = TimeUnit.MINUTES.toMillis(45);
        } else if (sessionTime < LATE_SESSION) {
            // Late session: 15-30 minutes
            minTime = TimeUnit.MINUTES.toMillis(15);
            maxTime = TimeUnit.MINUTES.toMillis(30);
        } else if (sessionTime < END_SESSION) {
            // End session: 10-20 minutes
            minTime = TimeUnit.MINUTES.toMillis(10);
            maxTime = TimeUnit.MINUTES.toMillis(20);
        } else {
            Log.info("Session time exceeding 8 hours, considering end of day break");
            // When session time exceeds 8 hours, break interval is very short until the long break
            minTime = TimeUnit.MINUTES.toMillis(5);
            maxTime = TimeUnit.MINUTES.toMillis(10);
        }
        // Use Calculations.random to get a random long between minTime (inclusive) and maxTime (exclusive)
        nextBreakIn = Calculations.random(minTime, maxTime);

        // Determine break duration based on session time
        if (sessionTime >= END_SESSION) {
            // End of day break: 6-10 hours
            nextBreakDuration = Calculations.random(TimeUnit.HOURS.toMillis(6), TimeUnit.HOURS.toMillis(10));
        } else if (sessionTime < EARLY_SESSION) {
            // Early session: 2-5 minutes
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(2), TimeUnit.MINUTES.toMillis(5));
        } else if (sessionTime < MID_SESSION) {
            // Mid session: 5-10 minutes
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(5), TimeUnit.MINUTES.toMillis(10));
        } else if (sessionTime < LATE_SESSION) {
            // Late session: 10-20 minutes
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(10), TimeUnit.MINUTES.toMillis(20));
        } else {
            // Extended breaks during end session: 15-30 minutes
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(30));
        }

        nextBreakTimer.reset();
        Log.debug(String.format("Next break in %s, duration: %s", formatTime(nextBreakIn), formatTime(nextBreakDuration)));
    }

    // Increment fatigueLevel based on session time
    public static boolean shouldIncreaseFatigue() {
        return fatigueTimer.elapsed() >= FATIGUE_INCREMENT_INTERVAL;
    }

    public static void increaseFatigue() {
        if (fatigueLevel < MAX_FATIGUE) {
            long sessionTime = sessionTimer.elapsed();
            int timeMultiplier = sessionTime > LATE_SESSION ? 3 : sessionTime > MID_SESSION ? 2 : 1;
            // For example, add 1 extra fatigue point for every 20 points of current fatigue
            int fatigueMultiplier = 1 + (fatigueLevel / 20);
            fatigueLevel += FATIGUE_INCREMENT * timeMultiplier * fatigueMultiplier;
            fatigueLevel = Math.min(fatigueLevel, MAX_FATIGUE);
            Log.info("Fatigue increased to: " + fatigueLevel);
        }
        fatigueTimer.reset();
    }

    // Get current reaction time based on fatigue level
    public static int getCurrentReactionTime() {
        double normalThreshold = 0.8 - (fatigueLevel * 0.005);       // 80% chance at 0 fatigue, 30% at max fatigue
        double predictableThreshold = 0.95 - (fatigueLevel * 0.004);  // 95% chance at 0 fatigue, 55% at max fatigue

        // Use Calculations.random to generate a double between 0.0 and 1.0
        double roll = Calculations.random(0.0, 1.0);
        if (roll < normalThreshold) {
            return ReactionGenerator.getNormal();
        } else if (roll < predictableThreshold) {
            return ReactionGenerator.getLowPredictable();
        } else {
            return ReactionGenerator.getAFK();
        }
    }

    // Reduce fatigue based on break duration
    private static void reduceFatigue(long breakDuration) {
        int reduction;
        long hours = breakDuration / TimeUnit.HOURS.toMillis(1);

        if (hours >= 6) {
            // Long break (6+ hours) – full reset and new session
            reduction = MAX_FATIGUE;
            sessionTimer.reset();
            Log.info("Long break completed – Starting new session");
        } else if (hours >= 1) {
            // Medium break (1+ hour) – significant reduction
            reduction = (int) (fatigueLevel * 0.6);
        } else if (breakDuration >= TimeUnit.MINUTES.toMillis(30)) {
            reduction = (int) (fatigueLevel * 0.3);
        } else {
            // Short break – small reduction
            reduction = (int) (fatigueLevel * 0.15);
        }

        fatigueLevel = Math.max(0, fatigueLevel - reduction);
        Log.info("Break over. Fatigue reduced by " + reduction + " to " + fatigueLevel);
    }

    // Check if it's time to take a break
    public static boolean shouldTakeBreak() {
        if (onBreak) return false;
        if (nextBreakIn == 0) {
            calculateNextBreak();
            return false;
        }
        return nextBreakTimer.elapsed() >= nextBreakIn;
    }

    // Start a break
    public static void startBreak() {
        onBreak = true;
        currentBreakDuration = nextBreakDuration;
        breakTimer.reset();
        Log.info("Taking a break for " + formatTime(currentBreakDuration) + " with fatigue level: " + fatigueLevel);
    }

    // Determine if the break is over
    public static boolean isBreakOver() {
        if (!onBreak) return true;
        if (breakTimer.elapsed() >= currentBreakDuration) {
            reduceFatigue(currentBreakDuration);
            onBreak = false;
            calculateNextBreak();
            return true;
        }
        return false;
    }

    // Reset the tracker
    public static void reset() {
        fatigueLevel = 0;
        onBreak = false;
        currentBreakDuration = 0;
        nextBreakIn = 0;
        nextBreakDuration = 0;
        fatigueTimer.reset();
        breakTimer.reset();
        nextBreakTimer.reset();
        sessionTimer.reset();
    }

    // Get the time remaining until the next break
    public static long getTimeUntilNextBreak() {
        return Math.max(0, nextBreakIn - nextBreakTimer.elapsed());
    }

    public static String getFormattedNextBreakIn() {
        return formatTime(getTimeUntilNextBreak());
    }

    public static String getFormattedBreakDuration() {
        return formatTime(currentBreakDuration);
    }

    public static String getFormattedNextBreakDuration() {
        return formatTime(nextBreakDuration);
    }

    public static String getFormattedSessionTime() {
        return formatTime(sessionTimer.elapsed());
    }

    public static long getRemainingBreakTime() {
        if (!onBreak) {
            return 0;
        }
        return Math.max(0, currentBreakDuration - breakTimer.elapsed());
    }

    // Get formatted remaining break time
    public static String getFormattedRemainingBreakTime() {
        return formatTime(getRemainingBreakTime());
    }

    private static String formatTime(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }
}
