package com.example.untitleddungeongame.entity;

import android.util.Pair;

import com.example.untitleddungeongame.hotbar.attacks.Attack;
import com.example.untitleddungeongame.hotbar.items.Item;
import com.example.untitleddungeongame.stats.Stat;
import com.example.untitleddungeongame.stats.StatType;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private final String name;

    protected int health;
    protected int attack;
    protected int defense;
    protected int speed;
    protected boolean alive = true;
    protected Item[] equipped = new Item[4];
    protected Attack[] attacks = new Attack[4];
    public List<Stat> statusEffects = new ArrayList<>();


    int maxHealth; // Controls max health

    public Entity(String name, int health, int attack, int defense, int speed) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.name = name;

        maxHealth = health;
    }

    public String takeDamage(Attack attack, int additionalDamage) {
        Pair<Integer, Stat> info = attack.use();
        Stat stat = getStatModifier(StatType.DEFENSE);
        int damage = info.first + additionalDamage;
        if (stat != null) {
            damage -= stat.getValue();
        }
        if (damage < 0) {
            damage = 1;
        }
        health -= damage;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
        if (info.second != null) {
            statusEffects.add(info.second);
        }
        return attack.getName() + " dealt " + damage + " damage to " + name;
    }

    public boolean heal(int heal) {
        System.out.println("Current health: " + health);
        System.out.println("Max health: " + maxHealth);
        if (health == maxHealth) return false; //Failed to heal

        if (health + heal < maxHealth) {
            health += heal;
            return true;
        }
        health = maxHealth;
        return true; //Maxed out health
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

    public int getMaxHealth() {
        return maxHealth;
    }

    public String getName() {
        return name;
    }

    public Item[] getEquipped() {
        return equipped;
    }
    public Attack[] getAttacks() { return attacks; }
    protected Stat getStatModifier(StatType stateType) {
        for (Stat stat: statusEffects) {
            if (stat.getType() == stateType){
                return stat;
            }
        }
        return null;
    }

    public boolean isDead() {
        return health <= 0;
    }
}
