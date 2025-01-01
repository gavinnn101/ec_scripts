package com.gavin101.breakhelper;

import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.math.Calculations;

public class BreakHelper {
    private static boolean needToBreak = false;
    private static final int[] whenToBreakRange = new int[]{ 1_800_000, 2_700_000 }; // 30 - 45 minutes
    private static final int[] breakTimeRange = new int[]{180_000, 600_000}; // 3 - 10 minutes
    private static Timer sessionTimer;
    private static Timer breakTimer;
    private static long nextBreakTime;
    private static long breakEndTime;

    public static void initialize() {
        sessionTimer = new Timer();
        breakTimer = new Timer();
        nextBreakTime = Calculations.random(whenToBreakRange[0], whenToBreakRange[1]);
    }

    public static boolean needToBreak() {
        if (!needToBreak && sessionTimer != null && sessionTimer.elapsed() >= nextBreakTime) {
            setNeedToBreak(true);
        }
        return needToBreak;
    }

    public static void setNeedToBreak(boolean value) {
        Log.info("Setting needToBreak: " + value);
        needToBreak = value;
        if (value) {
            breakTimer.reset();
            breakEndTime = Calculations.random(breakTimeRange[0], breakTimeRange[1]);
            Log.debug("Break end time set to: " + breakEndTime + " ms");
        }
    }

    public static boolean isBreakFinished() {
        return breakTimer.elapsed() >= breakEndTime;
    }

    public static void resetSessionTimer() {
        sessionTimer.reset();
        nextBreakTime = Calculations.random(whenToBreakRange[0], whenToBreakRange[1]);
        needToBreak = false;
    }

    public static String getTimeUntilBreak() {
        long timeLeft = nextBreakTime - sessionTimer.elapsed();
        return formatTime(Math.max(0, timeLeft));
    }

    public static String getBreakTimeLeft() {
        long timeLeft = breakEndTime - breakTimer.elapsed();
        return formatTime(Math.max(0, timeLeft));
    }

    public static long getBreakTimeLeftMs() {
        return Math.max(0, breakEndTime - breakTimer.elapsed());
    }

    private static String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
    }
}
