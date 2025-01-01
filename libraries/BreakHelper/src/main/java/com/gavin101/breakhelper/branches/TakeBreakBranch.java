package com.gavin101.breakhelper.branches;

import net.eternalclient.api.Client;
import net.eternalclient.api.events.LogoutEvent;
import net.eternalclient.api.events.random.RandomManager;
import net.eternalclient.api.frameworks.tree.Branch;

import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.math.Calculations;


public class TakeBreakBranch extends Branch {
    private static final class BreakConfig {
        static final int MIN_SESSION_TIME = 30 * 60 * 1000; // 30 minutes
        static final int MAX_SESSION_TIME = 45 * 60 * 1000; // 45 minutes
        static final int MIN_BREAK_TIME = 3 * 60 * 1000;    // 3 minutes
        static final int MAX_BREAK_TIME = 10 * 60 * 1000;   // 10 minutes
    }

    private final Timer sessionTimer;
    private final Timer breakTimer;
    private long nextBreakTime;
    private long breakEndTime;
    private boolean isOnBreak;

    public TakeBreakBranch() {
        this.sessionTimer = new Timer();
        this.breakTimer = new Timer();
        this.nextBreakTime = generateNextBreakTime();
        this.isOnBreak = false;
        Log.info("First break starts in: " +formatTime(this.nextBreakTime));
    }

    private long generateNextBreakTime() {
        return Calculations.random(BreakConfig.MIN_SESSION_TIME, BreakConfig.MAX_SESSION_TIME);
    }

    private long generateBreakDuration() {
        return Calculations.random(BreakConfig.MIN_BREAK_TIME, BreakConfig.MAX_BREAK_TIME);
    }

    public boolean isOnBreak() {
        if (!isOnBreak && sessionTimer.elapsed() >= nextBreakTime) {
            startBreak();
        }
        return isOnBreak;
    }

    private void startBreak() {
        isOnBreak = true;
        breakTimer.reset();
        breakEndTime = generateBreakDuration();
        Log.info("Starting break for duration: " + formatTime(breakEndTime));
        logoutOfGame();
    }

    private void logoutOfGame() {
        if (Client.isLoggedIn()) {
            Log.info("Logging out for a break");
            RandomManager.setAutoLoginEnabled(false);
            new LogoutEvent().setEventCompleteCondition(
                    () -> !Client.isLoggedIn(), Calculations.random(1250, 2500)
            ).execute();
        }
    }

    private boolean isBreakFinished() {
        return breakTimer.elapsed() >= breakEndTime;
    }

    private void endBreak() {
        sessionTimer.reset();
        nextBreakTime = generateNextBreakTime();
        isOnBreak = false;
        RandomManager.setAutoLoginEnabled(true);
        Log.info("Break finished. Next break in: " + formatTime(nextBreakTime));
    }

    public String getTimeUntilBreak() {
        return formatTime(Math.max(0, nextBreakTime - sessionTimer.elapsed()));
    }

    public String getBreakTimeLeft() {
        return formatTime(Math.max(0, breakEndTime - breakTimer.elapsed()));
    }

    public long getBreakTimeLeftMs() {
        return Math.max(0, breakEndTime - breakTimer.elapsed());
    }

    private String formatTime(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public boolean isValid() {
        if (!isOnBreak()) {
            return false;
        }

        if (isBreakFinished()) {
            endBreak();
            return false;
        }

        return true;
    }
}
