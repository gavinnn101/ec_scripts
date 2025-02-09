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

    private static final int FATIGUE_FRESH = 30;
    private static final int FATIGUE_TIRED = 50;
    private static final int FATIGUE_VERY_TIRED = 70;
    private static final int FATIGUE_SLEEP_NEEDED = 90;

    @Getter private static int fatigueLevel = 0;
    private static Timer fatigueTimer = new Timer();
    private static Timer breakTimer = new Timer();
    private static Timer nextBreakTimer = new Timer();

    // Break state
    @Getter private static boolean onBreak = false;
    @Getter private static long currentBreakDuration = 0;
    @Getter private static long nextBreakIn = 0;
    @Getter private static long nextBreakDuration = 0;
    @Getter private static boolean needsBreakSchedule = true;

    private static double dailyEnergyMultiplier = 0.8 + (Calculations.random(0.0, 0.4)); // 0.8-1.2x

    private static final double LOW_INTENSITY = 0.8;
    private static final double HIGH_INTENSITY = 1.3;
    private static double currentIntensity = 1.0;

    /**
     * Calculate the timing and duration of the next break based on current fatigue level.
     * This should only be called when needsBreakSchedule is true.
     */
    private static void calculateNextBreak() {
        Log.info("Calculating new break schedule at fatigue level: " + fatigueLevel);

        if (fatigueLevel >= FATIGUE_SLEEP_NEEDED) {
            // Time for "sleep" - long rest of 6-8 hours
            nextBreakDuration = Calculations.random(TimeUnit.HOURS.toMillis(6), TimeUnit.HOURS.toMillis(8));
            nextBreakIn = Calculations.random(TimeUnit.MINUTES.toMillis(5), TimeUnit.MINUTES.toMillis(15));
            Log.info("Scheduling long rest break due to high fatigue: " + fatigueLevel);
        }
        else if (fatigueLevel < FATIGUE_FRESH) {
            // Fresh: Longer intervals, shorter breaks
            nextBreakIn = Calculations.random(TimeUnit.MINUTES.toMillis(45), TimeUnit.MINUTES.toMillis(75));
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(5), TimeUnit.MINUTES.toMillis(15));
        }
        else if (fatigueLevel < FATIGUE_TIRED) {
            // Getting tired: Medium intervals, medium breaks
            nextBreakIn = Calculations.random(TimeUnit.MINUTES.toMillis(30), TimeUnit.MINUTES.toMillis(45));
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(25));
        }
        else if (fatigueLevel < FATIGUE_VERY_TIRED) {
            // Tired: Shorter intervals, longer breaks
            nextBreakIn = Calculations.random(TimeUnit.MINUTES.toMillis(20), TimeUnit.MINUTES.toMillis(35));
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(25), TimeUnit.MINUTES.toMillis(35));
        }
        else {
            // Very tired: Frequent breaks, longest breaks
            nextBreakIn = Calculations.random(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(25));
            nextBreakDuration = Calculations.random(TimeUnit.MINUTES.toMillis(35), TimeUnit.MINUTES.toMillis(45));
        }

        nextBreakTimer.reset();
        Log.debug(String.format("Next break in %s, duration: %s (fatigue: %d)",
                formatTime(nextBreakIn), formatTime(nextBreakDuration), fatigueLevel));
    }

    /**
     * Checks if fatigue should be increased based on time elapsed
     */
    public static boolean shouldIncreaseFatigue() {
        return fatigueTimer.elapsed() >= FATIGUE_INCREMENT_INTERVAL;
    }

    /**
     * Increases fatigue level using an exponential growth formula
     */
    public static void increaseFatigue() {
        if (fatigueLevel < MAX_FATIGUE && !onBreak) {
            // Higher base rate to ensure early increases
            double baseRate = 0.8 + (fatigueLevel * 0.01);  // Changed from 0.25

            // Combine various factors
            double variabilityMultiplier = dailyEnergyMultiplier
                    * currentIntensity
                    * getTimeOfDayMultiplier();

            double fatigueMultiplier = 1.0 + (Math.pow(fatigueLevel, 1.5) / 600.0);
            double increment = FATIGUE_INCREMENT * baseRate * fatigueMultiplier * variabilityMultiplier;

            fatigueLevel += (int) Math.round(increment);
            fatigueLevel = Math.min(fatigueLevel, MAX_FATIGUE);

            Log.info(String.format("Fatigue increased to: %d (baseRate=%.2f, fatigueMultiplier=%.2f, variabilityMultiplier=%.2f)",
                    fatigueLevel, baseRate, fatigueMultiplier, variabilityMultiplier));
        }
        fatigueTimer.reset();
    }

    /**
     * Check if it's time to take a scheduled break
     */
    public static boolean shouldTakeBreak() {
        if (onBreak) return false;

        // Calculate next break if needed
        if (needsBreakSchedule) {
            calculateNextBreak();
            needsBreakSchedule = false;
            return false;
        }

        // Check if it's time for the scheduled break
        return nextBreakTimer.elapsed() >= nextBreakIn;
    }

    /**
     * Start a scheduled break
     */
    public static void startBreak() {
        if (!onBreak) {
            onBreak = true;
            currentBreakDuration = nextBreakDuration;
            breakTimer.reset();
            Log.info("Taking a break for " + formatTime(currentBreakDuration) + " with fatigue level: " + fatigueLevel);
        }
    }

    /**
     * Check if current break is complete and apply fatigue reduction if it is
     */
    public static boolean isBreakComplete() {
        if (!onBreak) return true;

        if (breakTimer.elapsed() >= currentBreakDuration) {
            Log.info("Break is over.");
            // Break is over, reduce fatigue and prepare for next break cycle
            reduceFatigue(currentBreakDuration);
            updateActivityIntensity();
            onBreak = false;
            needsBreakSchedule = true;  // Signal that we need to calculate next break
            return true;
        }
        return false;
    }

    /**
     * Reduce fatigue based on break duration
     */
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

    // Periodically update intensity (maybe every 30-60 mins)
    private static void updateActivityIntensity() {
        currentIntensity = LOW_INTENSITY + Calculations.random(0.0, HIGH_INTENSITY - LOW_INTENSITY);
    }

    private static double getTimeOfDayMultiplier() {
        // Get current hour (0-23)
        int hour = java.time.LocalTime.now().getHour();

        // More fatigue late at night (10PM-6AM)
        if (hour >= 22 || hour < 6) return 1.2;
        // Less fatigue in morning (6AM-10AM)
        if (hour >= 6 && hour < 10) return 0.9;
        // Normal afternoon
        return 1.0;
    }

    // Reset all tracker state
    public static void reset() {
        fatigueLevel = 0;
        onBreak = false;
        currentBreakDuration = 0;
        nextBreakIn = 0;
        nextBreakDuration = 0;
        needsBreakSchedule = true;
        fatigueTimer.reset();
        breakTimer.reset();
        nextBreakTimer.reset();
    }

    // Utility methods for time formatting and remaining time calculations
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
        if (!onBreak) return 0;
        return Math.max(0, currentBreakDuration - breakTimer.elapsed());
    }

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
