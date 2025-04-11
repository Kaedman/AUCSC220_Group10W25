package com.example.untitleddungeongame.entity;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.animations.Sprite;
import com.example.untitleddungeongame.hotbar.attacks.QuickAttack;

public class Enemy extends Entity {



    public Enemy(String name, int health, int attack, int defense, int speed) {
        super(name, health, attack, defense, speed);
        attacks[0] = new QuickAttack();
    }
}
