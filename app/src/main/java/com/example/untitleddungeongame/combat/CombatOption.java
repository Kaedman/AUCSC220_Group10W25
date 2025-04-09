package com.example.untitleddungeongame.combat;

import com.example.untitleddungeongame.hotbar.HotBarInfo;
import com.example.untitleddungeongame.hotbar.attacks.Attack;
import com.example.untitleddungeongame.hotbar.items.Item;

public class CombatOption<T extends HotBarInfo> {

    public enum CombatOptionType {
        ATTACK,
        ITEM
    }
    private final CombatOptionType type;
    private final int position;
    public CombatOption(T param, int pos) {
        if (param instanceof Attack) {
            type = CombatOptionType.ATTACK;
            position = pos;
        } else if (param instanceof Item) {
            type = CombatOptionType.ITEM;
            position = pos;
        } else {
            throw new IllegalArgumentException("Invalid type: " + param.getClass().getName());
        }
    }

    public CombatOptionType getType() {
        return type;
    }
    public int getPosition() {
        return position;
    }
}