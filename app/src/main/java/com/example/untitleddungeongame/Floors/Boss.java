package com.example.untitleddungeongame.Floors;

import com.example.untitleddungeongame.Enemy;

public class Boss extends Room {
    private Enemy boss = null;
    private boolean isCleared;

    public Boss(int roomId, Enemy boss) {
        super(roomId);
        this.boss = boss;
        this.isCleared = false;
    }

    public boolean checkCleared() {
        if (boss.getHp() <= 0) {
            isCleared = true;
        }

        return isCleared;
    }

    private Room getNewFloor() {
        return RoomMaster.generateRooms();
    }
}
