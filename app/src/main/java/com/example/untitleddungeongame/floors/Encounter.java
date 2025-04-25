package com.example.untitleddungeongame.floors;

import com.example.untitleddungeongame.entity.Enemy;
import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.handlers.combat.Combat;


public class Encounter extends Room {
    private Combat combat;


    private Player player;

    public Encounter(int roomId, Enemy enemy, Player player) {
        super(roomId, enemy);
        this.player = player;
        this.enemy = enemy;

    }

}
