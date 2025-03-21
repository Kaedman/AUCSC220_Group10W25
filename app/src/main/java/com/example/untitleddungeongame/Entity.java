package com.example.untitleddungeongame;

public class Entity {
    private int hp;
    private int maxHp;
    private int baseAttack;

    public Entity(int hp, int maxHp, int baseAttack) {
        this.baseAttack = baseAttack;
        this.hp = hp;
        this.maxHp = maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
    }

    public int calculateAttack() {
        return baseAttack;
    }
}
