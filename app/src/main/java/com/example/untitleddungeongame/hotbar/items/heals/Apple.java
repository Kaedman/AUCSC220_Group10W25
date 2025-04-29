package com.example.untitleddungeongame.hotbar.items.heals;

import com.example.untitleddungeongame.entity.Entity;
import com.example.untitleddungeongame.hotbar.items.Item;

public class Apple extends Item {
    public Apple() {
        super("Apple", 5, 5);
    }

    public boolean use(int position, Entity character) {
        if (character.heal(5)) {
            drop(1, position, character.getEquipped());
            return true;
        }
        return false;
    }

}
