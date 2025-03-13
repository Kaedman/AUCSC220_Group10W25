package com.example.untitleddungeongame;

public class Player extends Entity {
    private int money;

    public Player(String type, int hp, int maxHp, int baseAttack, int money) {
        super(hp, maxHp, baseAttack);
        this.money = money;
    }
}
