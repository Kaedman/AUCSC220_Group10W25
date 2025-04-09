package com.example.untitleddungeongame.hotbar.attacks;

import android.util.Pair;

import com.example.untitleddungeongame.hotbar.HotBarInfo;
import com.example.untitleddungeongame.stats.Stat;

public abstract class Attack extends HotBarInfo {
    private final String name;
    private final int damage;

    public Attack(String name, int damage) {
        super(name);
        this.name = name;
        this.damage = damage;
    }

    abstract public Pair<Integer, Stat> use();

    public String getName() {
        return name;
    }

    public int getInfo() {
        return damage;
    }

}
