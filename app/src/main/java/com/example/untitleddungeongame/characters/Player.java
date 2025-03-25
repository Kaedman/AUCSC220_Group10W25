package com.example.untitleddungeongame.characters;

import com.example.untitleddungeongame.items.Item;

public class Player extends Character {

    Item[] inventory = new Item[10];


    public Player(int health) {
        super(health, 10, 10, 10);
    }


    //Should we super these???
    public void setHealthWhenHit(int damage) {
        if (super.health - damage < 0) {
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

    public int calculateReceivedDamage(int initialDamage){

        //Imma just yoink terraria's defense stats. Cry about it
        return initialDamage - defense/2;

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

