package com.example.untitleddungeongame.floors;

import com.example.untitleddungeongame.Enemy;

public class Boss extends Room {
    private Enemy boss = null;
    private boolean isCleared;
    RoomMaster nextFloor;

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

    private RoomMaster getNewFloor() {
        nextFloor.generateRoomArray(5, 5, 10);
        return nextFloor;
    }

    public void updateRoom() {
        if (checkCleared()) {
            //showFloorMenu();
        }
    }
}
