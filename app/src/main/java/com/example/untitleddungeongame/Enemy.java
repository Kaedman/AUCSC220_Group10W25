package com.example.untitleddungeongame;

public class Enemy extends Entity{
    private String type = null;

    public Enemy(String type, int hp, int maxHp, int baseAttack) {
        super(hp, maxHp, baseAttack);
        this.type = type;
    }
}
