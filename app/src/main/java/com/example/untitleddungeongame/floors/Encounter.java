package com.example.untitleddungeongame.floors;

import com.example.untitleddungeongame.Enemy;

public class Encounter extends Room {
    private Enemy enemy = null;

    public Encounter(int roomId, Enemy enemy) {
        super(roomId);
        this.enemy = enemy;
    }

    public boolean isCleared() {
        return enemy.getHp() <= 0;
    }

    public void updateRoom() {
        if (isCleared()) {

        }
    }
}
