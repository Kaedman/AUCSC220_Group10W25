package com.example.untitleddungeongame.characters;

import com.example.untitleddungeongame.items.Item;
import com.example.untitleddungeongame.stats.Stat;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private String name;

    protected int health;
    protected int attack;
    protected int defense;
    protected int speed;
    public Item[] equipped = new Item[4];
    public List<Stat> statusEffects = new ArrayList<>();

    int maxHealth; // Controls max health

    public Character(int health, int attack, int defense, int speed) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;

        maxHealth = health;
    }



    public void attack(int damage) {
        if (health - damage < 0) {
            health = 0;
        } else {
            health -= damage;
        }
    }

    public boolean heal(int heal) {
        if (health + heal < maxHealth) {
            health += heal;
            return true;
        }
        health = maxHealth;
        return false;
    }

    public boolean useItem(int position) {
        if (equipped[position] != null) {
            return equipped[position].use(position, this);
        }
        return false;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }
}
