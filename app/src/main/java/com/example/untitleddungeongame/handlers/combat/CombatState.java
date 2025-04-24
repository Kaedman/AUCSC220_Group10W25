package com.example.untitleddungeongame.handlers.combat;

import java.util.HashMap;
import java.util.Objects;

public class CombatState {
    private State currentState;
    private State previousState;

    public final CombatTurn combatTurn = new CombatTurn();
    public enum State {
        READY,
        WAITING,
        TURN_START,
        ATTACK,
        ITEM_USE,
        STATUS_EFFECT,
        PARRY,
        ENDING,
    }

    private final HashMap<State, Runnable> stateCallbacks = new HashMap<>();

    public CombatState() {
        currentState = State.READY;
        previousState = State.READY;
    }

    public void runState() {
        if (stateCallbacks.containsKey(currentState)) {
            Objects.requireNonNull(stateCallbacks.get(currentState)).run();
        }
    }

    public void switchState(State newState) {
        if (currentState == newState) return;
        System.out.println("Switching state from " + currentState + " to " + newState);
        if (newState == State.TURN_START && (
                currentState == State.ATTACK
                || currentState == State.ITEM_USE
                || currentState == State.STATUS_EFFECT
                || currentState == State.PARRY
        )) {
            System.out.println("Switching to combat turn");
            combatTurn.switchTurn();
        }
        previousState = currentState;
        currentState = newState;
    }

    public boolean isReady() {
        return currentState == State.READY;
    }

    public void setStateCallback(State state, Runnable callback) {
        stateCallbacks.put(state, callback);
    }

    public CombatTurn.State currentTurn() {
        return combatTurn.currentTurn();
    }
    public State getCurrentState() {
        return currentState;
    }
    public void reset() {
        currentState = State.READY;
        previousState = State.READY;
        combatTurn.reset();
    }
}
