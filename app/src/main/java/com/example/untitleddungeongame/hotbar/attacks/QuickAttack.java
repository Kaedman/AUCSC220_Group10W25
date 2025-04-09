package com.example.untitleddungeongame.hotbar.attacks;

import android.util.Pair;

import com.example.untitleddungeongame.stats.Stat;

public class QuickAttack extends Attack {

    public QuickAttack() {
        super("Quick Attack", 5);
    }

    public Pair<Integer, Stat> use() {
        int damage = getInfo();
        return new Pair<>(damage, null);
    }
}
