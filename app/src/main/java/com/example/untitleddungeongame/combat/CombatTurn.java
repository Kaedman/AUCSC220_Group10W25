package com.example.untitleddungeongame.combat;

public class CombatTurn {
    private State currentTurn;
    private State lastTurn;
    enum State {
        PLAYER_TURN,
        ENEMY_TURN,
        NONE
    }

    public CombatTurn() {
        currentTurn = State.NONE;
        lastTurn = State.NONE;
    }

    public void initializePlayerTurn() {
        currentTurn = State.PLAYER_TURN;
        lastTurn = State.PLAYER_TURN;
    }

    public void initializeEnemyTurn() {
        currentTurn = State.ENEMY_TURN;
        lastTurn = State.ENEMY_TURN;
    }

    public void switchTurn() {
        if (currentTurn == State.PLAYER_TURN) {

            if (lastTurn == State.ENEMY_TURN) {
                currentTurn = State.NONE;
            } else if (lastTurn == State.PLAYER_TURN) {
                currentTurn = State.ENEMY_TURN;
            }
            lastTurn = State.PLAYER_TURN;

        } else if (currentTurn == State.ENEMY_TURN) {
            if (lastTurn == State.PLAYER_TURN) {
                currentTurn = State.NONE;
            } else if (lastTurn == State.ENEMY_TURN) {
                currentTurn = State.PLAYER_TURN;
            }
            lastTurn = State.ENEMY_TURN;
        }
    }

    public boolean isPlayerTurn() {
        return currentTurn == State.PLAYER_TURN;
    }

    public boolean isEnemyTurn() {
        return currentTurn == State.ENEMY_TURN;
    }

}
