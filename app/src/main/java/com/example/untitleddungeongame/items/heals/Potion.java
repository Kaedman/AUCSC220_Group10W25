package com.example.untitleddungeongame.items.heals;

import com.example.untitleddungeongame.characters.Character;
import com.example.untitleddungeongame.items.Item;

public class Potion extends Item {
    public Potion() {
        super("Potion", 5);
    }

    public boolean use(int position, Character character) {
        if (character.heal(10)) {
            drop(1, position, character.equipped);
            return true;
        }
        return false;
    }

}
