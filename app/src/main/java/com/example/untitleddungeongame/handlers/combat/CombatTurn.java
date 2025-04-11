package com.example.untitleddungeongame.handlers.combat;

public class CombatTurn {
    private State currentTurn;

    private boolean playerTurnHappened = false;
    private boolean enemyTurnHappened = false;
    public enum State {
        PLAYER,
        ENEMY,
        NONE
    }

    public CombatTurn() {
        currentTurn = State.NONE;
    }

    public void initializePlayerTurn() {
        currentTurn = State.PLAYER;

    }

    public void initializeEnemyTurn() {
        currentTurn = State.ENEMY;
    }

    public void switchTurn() {
        if (currentTurn == State.PLAYER) {
            if (!enemyTurnHappened) {
                currentTurn = State.ENEMY;
            } else {
                currentTurn = State.NONE;
            }
            playerTurnHappened = true;

        } else if (currentTurn == State.ENEMY) {
            if (!playerTurnHappened) {
                currentTurn = State.PLAYER;
            } else {
                currentTurn = State.NONE;
            }
            enemyTurnHappened = true;
        } else if (currentTurn == State.NONE) {
            resetTurns();
        }
    }

    public void resetTurns() {
        currentTurn = State.NONE;
        playerTurnHappened = false;
        enemyTurnHappened = false;
    }

    public State currentTurn() {
        return currentTurn;
    }

    public boolean hasPlayerTurnHappened() {
        return playerTurnHappened;
    }
    public boolean hasEnemyTurnHappened() {
        return enemyTurnHappened;
    }
    public void reset() {
        if (currentTurn == State.NONE) {
            return;
        }
        currentTurn = State.NONE;
        playerTurnHappened = false;
        enemyTurnHappened = false;
    }
}
