package com.example.untitleddungeongame.items.heals;

import com.example.untitleddungeongame.characters.Entity;
import com.example.untitleddungeongame.items.Item;

public class Apple extends Item {
    public Apple() {
        super("Apple", 5);
    }

    public boolean use(int position, Entity character) {
        if (character.heal(5)) {
            drop(1, position, character.getEquipped());
            return true;
        }
        return false;
    }

}
