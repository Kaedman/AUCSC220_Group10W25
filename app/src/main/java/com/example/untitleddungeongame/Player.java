package com.example.untitleddungeongame;

public class Player {
    protected int health;
    protected int attack;
    protected int defense;
    protected int speed;

    public Player(int health) {
        this.health = health;
        this.attack = 5;
        this.defense = 5;
        this.speed = 5;
    }

    public void setHealthWhenHit(int damage) {
        if (health - damage < 0) {
            health = 0;
        } else {
            health -= damage;
        }
    }

    public void setHealthWhenHeal(int heal) {
        if (health + heal > 100) {
            health = 100;
        } else {
            health += heal;
        }
    }
}

