package com.example.untitleddungeongame.items.heals;

import com.example.untitleddungeongame.items.Item;
import com.example.untitleddungeongame.characters.Character;

public class Apple extends Item {
    public Apple() {
        super("Apple", 5);
    }

    public boolean use(int position, Character character) {
        if (character.heal(5)) {
            drop(1, position, character.equipped);
            return true;
        }
        return false;
    }

}
