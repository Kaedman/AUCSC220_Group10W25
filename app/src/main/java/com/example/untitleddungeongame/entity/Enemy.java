package com.example.untitleddungeongame.entity;

import com.example.untitleddungeongame.hotbar.attacks.QuickAttack;

public class Enemy extends Entity {

    public Enemy(String name, int health) {

        super(name, health, 10, 10, 10);
        attacks[0] = new QuickAttack();
    }
}
