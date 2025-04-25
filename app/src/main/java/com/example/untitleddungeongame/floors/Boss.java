package com.example.untitleddungeongame.floors;

import com.example.untitleddungeongame.entity.Enemy;

public class Boss extends Room{
    private boolean isCleared;


    RoomMaster roomMaster;

    public Boss(int roomId, Enemy boss) {
        super(roomId, boss);
        this.isCleared = false;
    }


}
