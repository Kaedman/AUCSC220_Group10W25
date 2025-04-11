package com.example.untitleddungeongame.handlers.combat;

import com.example.untitleddungeongame.misc.ElapseTime;

public class ParryController {
    protected boolean playerParrying = false;
    protected boolean playerParrySet = false;
    protected boolean screenTapped = false;
    private final ElapseTime parryTime = new ElapseTime();

    public ParryController() {
    }

    public void setParrying(boolean state) {
        playerParrying = state;
        playerParrySet = state;
    }

    public boolean isTryingToParry() {
        if (parryTime.hasTimeElapsed(500) && screenTapped) {
            screenTapped = false;
            return true;
        }
        return false;
    }

    public boolean hasParryBeenSet() {
        if (playerParrySet && playerParrying) {
            playerParrySet = false;
            return true;
        }
        return false;
    }
    public void reset() {
        playerParrying = false;
        playerParrySet = false;
        screenTapped = false;
    }
}
