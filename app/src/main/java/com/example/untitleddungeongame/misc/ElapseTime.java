package com.example.untitleddungeongame.misc;

public class ElapseTime {
    static long currentTime = System.currentTimeMillis();
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

        if (currentTime - lastTime > time) {
            lastTimeSet = false;
            return true;
        }

        return false;
    }
}
