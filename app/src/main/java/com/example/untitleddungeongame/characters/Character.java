package com.example.untitleddungeongame.characters;

import com.example.untitleddungeongame.items.Item;

public class Character {
    private String name;

    int health;
    int attack;
    int defense;
    int speed;
    Item[] equipped = new Item[4];

    int maxHealth; // Controls max health

    public Character(int health, int attack, int defense, int speed) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;

        maxHealth = health;
    }



    public void setHealthWhenHit(int damage) {
        if (health - damage < 0) {
            health = 0;
        } else {
            health -= damage;
        }
    }

    public void setHealthWhenHeal(int heal) {
        if (health + heal > maxHealth) {
            health = maxHealth;
        } else {
            health += heal;
        }
    }
}
