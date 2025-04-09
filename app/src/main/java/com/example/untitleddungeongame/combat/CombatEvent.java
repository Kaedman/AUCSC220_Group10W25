package com.example.untitleddungeongame.combat;

import com.example.untitleddungeongame.characters.Entity;

public class CombatEvent {
    private Entity source;
    private Entity target;
    private CombatOption.CombatOptionType type;
    private int position;

    public void set(Entity source, Entity target, CombatOption.CombatOptionType type, int position) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.position = position;
    }

    public Entity getSource() {
        return source;
    }
    public Entity getTarget() {
        return target;
    }
    public CombatOption.CombatOptionType getType() {
        return type;
    }
    public int getPosition() {
        return position;
    }
}
