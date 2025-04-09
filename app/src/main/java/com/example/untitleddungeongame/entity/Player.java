package com.example.untitleddungeongame.entity;

import com.example.untitleddungeongame.hotbar.attacks.Attack;
import com.example.untitleddungeongame.hotbar.items.Item;

public class Player extends Entity {

    Item[] inventory = new Item[10];


    public Player(int health) {
        super("Player", health, 10, 10, 10);
    }


    //Should we super these???
    public boolean addItem(Item item) {
        boolean itemEquipped = false;
        for (int i = 0; i < equipped.length; i++) {
            if (equipped[i] == null) {
                itemEquipped = item.add(1);
                equipped[i] = item;
                break;
            } else if (equipped[i].getName().equals(item.getName())) {
                itemEquipped = item.add(1);
                break;
            }
        }
        if (itemEquipped) return true;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                itemEquipped = item.add(1);
                inventory[i] = item;
                break;
            } else if (inventory[i].getName().equals(item.getName())) {
                itemEquipped = item.add(1);
                break;
            }
        }
        // TODO: Add a way to drop items if inventory is full
        // TODO: Check if it
        return itemEquipped;
    }

    public boolean addAttack(Attack attack) {
        for (int i = 0; i < attacks.length; i++) {
            if (attacks[i] != null && attack.getName().equals(attacks[i].getName())) {
                return false;
            }
            if (attacks[i] == null) {
                attacks[i] = attack;
                return true;
            }
        }
        return false;
    }

    public int calculateReceivedDamage(int initialDamage){

        //Imma just yoink terraria's defense stats. Cry about it
        return initialDamage - defense/2;
    }
}

