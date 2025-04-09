package com.example.untitleddungeongame.characters;

import com.example.untitleddungeongame.hotbar.attacks.QuickAttack;

public class NPC extends Entity {

    public NPC(String name, int health) {

        super(name, health, 10, 10, 10);
        attacks[0] = new QuickAttack();
    }
}
