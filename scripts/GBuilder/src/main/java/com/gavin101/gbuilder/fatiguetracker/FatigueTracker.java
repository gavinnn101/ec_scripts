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
    private static final long EARLY_SESSION = TimeUnit.HOURS.toMillis(3);
    private static final long MID_SESSION = TimeUnit.HOURS.toMillis(6);
    private static final long LATE_SESSION = TimeUnit.HOURS.toMillis(9);
    private static final long END_SESSION = TimeUnit.HOURS.toMillis(12);

    private static final int FATIGUE_FRESH = 30;
    private static final int FATIGUE_TIRED = 50;
    private static final int FATIGUE_VERY_TIRED = 70;
    private static final int FATIGUE_SLEEP_NEEDED = 90;

    @Getter private static int fatigueLevel = 0;
    private static Timer fatigueTimer = new Timer();
    private static Timer breakTimer = new Timer();
    private static Timer nextBreakTimer = new Timer();

    @Getter private static boolean onBreak = false;
    @Getter private static long currentBreakDuration = 0;
    @Getter private static long nextBreakIn = 0;
    @Getter private static long nextBreakDuration = 0;

    private static void calculateNextBreak() {
        // First check if we need a long rest
        if (fatigueLevel >= FATIGUE_SLEEP_NEEDED) {
            // Time for "sleep" - long rest of 6-8 hours
            nextBreakDuration = Calculations.random(TimeUnit.HOURS.toMillis(6), TimeUnit.HOURS.toMillis(8));
            // Short interval until this needed long break
            nextBreakIn = Calculations.random(TimeUnit.MINUTES.toMillis(5), TimeUnit.MINUTES.toMillis(15));
            Log.info("Scheduling long rest break due to high fatigue: " + fatigueLevel);
            return;
        }

        // Normal break scheduling based on fatigue
        if (fatigueLevel < FATIGUE_FRESH) {
            // Fresh: Longer intervals (45-75 mins), shorter breaks (5-15 mins)
            nextBreakIn = Calculations.random(TimeUnit.MINUTES.toMillis(45), TimeUnit.MINUTES.toMillis(75));
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(5), TimeUnit.MINUTES.toMillis(15));
        }
        else if (fatigueLevel < FATIGUE_TIRED) {
            // Getting tired: Medium intervals (30-45 mins), medium breaks (15-25 mins)
            nextBreakIn = Calculations.random(TimeUnit.MINUTES.toMillis(30), TimeUnit.MINUTES.toMillis(45));
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(25));
        }
        else if (fatigueLevel < FATIGUE_VERY_TIRED) {
            // Tired: Shorter intervals (20-35 mins), longer breaks (25-35 mins)
            nextBreakIn = Calculations.random(TimeUnit.MINUTES.toMillis(20), TimeUnit.MINUTES.toMillis(35));
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(25), TimeUnit.MINUTES.toMillis(35));
        }
        else {
            // Very tired: Frequent breaks (15-25 mins), longest breaks (35-45 mins)
            nextBreakIn = Calculations.random(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(25));
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(35), TimeUnit.MINUTES.toMillis(45));
        }

        nextBreakTimer.reset();
        Log.debug(String.format("Next break in %s, duration: %s (fatigue: %d)",
                formatTime(nextBreakIn), formatTime(nextBreakDuration), fatigueLevel));
    }

    // Increment fatigueLevel based on session time
    public static boolean shouldIncreaseFatigue() {
        return fatigueTimer.elapsed() >= FATIGUE_INCREMENT_INTERVAL;
    }

    public static void increaseFatigue() {
        if (fatigueLevel < MAX_FATIGUE) {
            // Base rate increases with fatigue level
            double baseRate = 0.5 + (fatigueLevel * 0.015); // Starts at 0.5, increases to about 2.0 at max fatigue

            // Fatigue multiplier remains for exponential growth
            double fatigueMultiplier = 1.0 + (Math.pow(fatigueLevel, 1.5) / 400.0);

            // Calculate final increment
            double increment = FATIGUE_INCREMENT * baseRate * fatigueMultiplier;

            fatigueLevel += (int) Math.round(increment);
            fatigueLevel = Math.min(fatigueLevel, MAX_FATIGUE);

            Log.info(String.format("Fatigue increased to: %d (baseRate=%.2f, fatigueMultiplier=%.2f)",
                    fatigueLevel, baseRate, fatigueMultiplier));
        }
        fatigueTimer.reset();
    }

    // Reduce fatigue based on break duration
    private static void reduceFatigue(long breakDuration) {
        int reduction;
        long hours = breakDuration / TimeUnit.HOURS.toMillis(1);
        long minutes = breakDuration / TimeUnit.MINUTES.toMillis(1);

        if (hours >= 6) {
            // Long rest (6+ hours) - full reset
            reduction = MAX_FATIGUE;
            Log.info("Long rest completed – Starting fresh session");
        } else if (hours >= 2) {
            // Extended break (2-6 hours) - major recovery
            double reductionPercent = Math.min(0.9, 0.7 + (hours * 0.05));
            reduction = (int)(fatigueLevel * reductionPercent);
        } else if (hours >= 1) {
            // Long break (1-2 hours) - significant recovery
            double reductionPercent = Math.min(0.7, 0.5 + (hours * 0.1));
            reduction = (int)(fatigueLevel * reductionPercent);
        } else if (minutes >= 30) {
            // Medium break (30-60 mins) - moderate recovery
            double reductionPercent = Math.min(0.5, 0.3 + (minutes * 0.005));
            reduction = (int)(fatigueLevel * reductionPercent);
        } else {
            // Short break (<30 mins) - minor recovery
            double reductionPercent = Math.min(0.3, 0.15 + (minutes * 0.005));
            reduction = (int)(fatigueLevel * reductionPercent);
        }

        // Additional recovery boost for well-timed breaks (before extreme fatigue)
        if (fatigueLevel < 70) {
            reduction = (int)(reduction * 1.2); // 20% bonus recovery for proactive breaks
        }

        int oldFatigue = fatigueLevel;
        fatigueLevel = Math.max(0, fatigueLevel - reduction);
        Log.info(String.format("Break over (%d minutes). Fatigue reduced from %d to %d (-%d)",
                minutes, oldFatigue, fatigueLevel, reduction));
    }

    // Get current reaction time based on fatigue level
    public static int getCurrentReactionTime() {
        // Early session (0-33 fatigue): High predictable, some normal, minimal AFK
        // Mid session (34-66 fatigue): Mostly normal, less predictable, some AFK
        // Late session (67-100 fatigue): More AFK, some normal, minimal predictable

        double predictableChance;
        double normalChance;
        double afkChance;

        if (fatigueLevel <= 33) {
            // Early session: 60-20% predictable, 39-75% normal, 1-5% AFK
            predictableChance = 0.60 - (fatigueLevel * 0.012);  // Decrease from 60% to 20%
            normalChance = 0.39 + (fatigueLevel * 0.011);      // Increase from 39% to 75%
            afkChance = 0.01 + (fatigueLevel * 0.001);        // Slight increase from 1% to 5%
        }
        else if (fatigueLevel <= 66) {
            // Mid session: 20-5% predictable, 75-65% normal, 5-30% AFK
            predictableChance = 0.20 - (fatigueLevel - 33) * 0.004;  // Decrease from 20% to 5%
            normalChance = 0.75 - (fatigueLevel - 33) * 0.003;      // Decrease from 75% to 65%
            afkChance = 0.05 + (fatigueLevel - 33) * 0.007;        // Increase from 5% to 30%
        }
        else {
            // Late session: 5-0% predictable, 65-20% normal, 30-80% AFK
            predictableChance = Math.max(0, 0.05 - (fatigueLevel - 66) * 0.001);  // Decrease from 5% to 0%
            normalChance = 0.65 - (fatigueLevel - 66) * 0.013;                    // Decrease from 65% to 20%
            afkChance = 0.30 + (fatigueLevel - 66) * 0.014;                      // Increase from 30% to 80%
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
    }

    // Get the time remaining until the next break
    public static long getTimeUntilNextBreak() {
        return Math.max(0, nextBreakIn - nextBreakTimer.elapsed());
    }

    public static String getFormattedNextBreakIn() {
        return formatTime(getTimeUntilNextBreak());
    }

    public static String getFormattedNextBreakDuration() {
        return formatTime(nextBreakDuration);
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
