package com.example.untitleddungeongame.floors;

import com.example.untitleddungeongame.Enemy;

public class Encounter extends Room {
    private Enemy enemy = null;
    private Boolean isCleared;
    public Encounter(int roomId, Enemy enemy) {
        super(roomId);
        this.enemy = enemy;
        this.isCleared = false;
    }

    public boolean checkCleared() {
        if (enemy.getHp() <= 0) {
            isCleared = true;
        }

        return isCleared;
    }
}
