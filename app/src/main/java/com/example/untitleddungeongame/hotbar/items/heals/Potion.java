package com.example.untitleddungeongame.hotbar.items.heals;

import com.example.untitleddungeongame.characters.Entity;
import com.example.untitleddungeongame.hotbar.items.Item;

public class Potion extends Item {
    public Potion() {
        super("Potion", 5);
    }

    public boolean use(int position, Entity character) {
        if (character.heal(10)) {
            drop(1, position, character.getEquipped());
            return true;
        }
        return false;
    }

}
