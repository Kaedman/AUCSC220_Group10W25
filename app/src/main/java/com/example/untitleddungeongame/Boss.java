package com.example.untitleddungeongame;

public class Boss extends Room{
    private Enemy boss = null;
    private boolean isCleared;

    public Boss(Room left, Room right, Room up, Room down, Enemy boss) {
        super(left, right, up, down);
        this.boss = boss;
        this.isCleared = false;
    }

    public boolean checkCleared() {
        if (boss.getHp() <= 0) {
            isCleared = true;
        }

        return isCleared;
    }
}
