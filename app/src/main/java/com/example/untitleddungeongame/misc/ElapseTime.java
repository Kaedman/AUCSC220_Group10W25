package com.example.untitleddungeongame.misc;

public class ElapseTime {
    static private long currentTime = System.currentTimeMillis();
    private long lastTime = System.currentTimeMillis();
    private boolean lastTimeSet = false;

    public static void update() {
        currentTime = System.currentTimeMillis();
    }

    /**
     * Returns true if the time has elapsed since the last time set
     * @param time the time to check if it has elapsed in milliseconds
     * @return true if the time has elapsed
     */
    public boolean hasTimeElapsed(long time) {
        if (!lastTimeSet) {
            lastTime = currentTime;
            lastTimeSet = true;
        }

        if (currentTime - lastTime < time) return false;

        lastTimeSet = false;
        return true;
    }

    /**
     * Returns true if the time has elapsed since the last time set
     * @param time the time to check if it has elapsed in seconds
     * @return true if the time has elapsed
     */
    public boolean hasTimeElapsedSeconds(long time) {
        return hasTimeElapsed(time * 1000);
    }

    /**
     * Returns true if the time has elapsed since the last time set
     * @param time the time to check if it has elapsed in minutes
     * @return true if the time has elapsed
     */
    public boolean hasTimeElapsedMinutes(long time) {
        return hasTimeElapsed(time * 60 * 1000);
    }

    public void reset() {
        lastTimeSet = false;
    }

    public static long getCurrentTime() {
        return currentTime;
    }
    public static Double getCurrentTimeSeconds() {
        return (double) currentTime / 1000;
    }

}
