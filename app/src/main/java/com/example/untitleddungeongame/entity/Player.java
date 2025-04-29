package com.example.untitleddungeongame.entity;

import com.example.untitleddungeongame.hotbar.attacks.Attack;
import com.example.untitleddungeongame.hotbar.items.Item;

public class Player extends Entity {
    int money;

    public Player(int health) {
        super("Player", health, 10, 10, 10);
    }
    public Player(int health, int attack, int defense, int speed) {
        super("Player", health, attack, defense, speed);
        this.money = 0;
    }

    public boolean addItem(Item item) {
        boolean itemEquipped = false;
        for (int i = 0; i < equipped.length; i++) {
            if (equipped[i] == null) {
                itemEquipped = item.add(1);
                equipped[i] = item;
                break;
            } else if (equipped[i].getName().equals(item.getName())) {
                if (equipped[i].getInfo() < 5) {
                    // Fixed a bug where inventory was not equipping the item
                    itemEquipped = item.add(1);
                    item.add(1);
                    equipped[i] = item;
                }
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        if (money <= 0) {
            this.money = 0;
            return;
        }
        this.money = money;
    }
}

