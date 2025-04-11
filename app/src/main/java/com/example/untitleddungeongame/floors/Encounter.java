package com.example.untitleddungeongame.floors;

import com.example.untitleddungeongame.Enemy;
import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.handlers.combat.Combat;


public class Encounter extends Room {
    private Enemy enemy = null;
    private Combat combat;
    private Player player;

    public Encounter(int roomId, Enemy enemy, Player player) {
        super(roomId);
        this.player = player;
        this.enemy = enemy;

    }

    @Override
    public boolean getRoomCleared(){
        return enemy.getHp() <= 0;
    }

}
