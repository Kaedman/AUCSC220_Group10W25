package com.example.untitleddungeongame.characters;

import com.example.untitleddungeongame.items.Item;

public class Character {
    private String name;

    int health;
    int attack;
    int defense;
    int speed;
    Item[] equipped = new Item[4];

    public Character(int health, int attack, int defense, int speed) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
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
