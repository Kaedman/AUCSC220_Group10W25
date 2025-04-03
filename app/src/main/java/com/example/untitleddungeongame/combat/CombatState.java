package com.example.untitleddungeongame.combat;

import androidx.annotation.Nullable;

public class CombatState {
    private State currentState;
    enum State {
        READY,
        WAITING,
        ATTACKING,
        PARRYING,
    }

    public CombatState() {
        currentState = State.READY;
    }

    public void switchState(boolean isParrying) {
        switch (currentState) {
            case READY:
                currentState = State.WAITING;
                break;

            case WAITING:
                currentState = State.ATTACKING;
                break;

            case ATTACKING:
                if (isParrying) {
                    currentState = State.PARRYING;
                } else {
                    currentState = State.READY;
                }
                break;
            case PARRYING:
                currentState = State.ATTACKING;
                break;
        }
    }

    public boolean isReady() {
        return currentState == State.READY;
    }

    public boolean isWaiting() {
        return currentState == State.WAITING;
    }

    public boolean isAttacking() {
        return currentState == State.ATTACKING;
    }

    public boolean isParrying() {
        return currentState == State.PARRYING;
    }

}
