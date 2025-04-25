package com.example.untitleddungeongame.hotbar.attacks;

import android.util.Pair;

import com.example.untitleddungeongame.stats.Stat;

public class BigSlamma extends Attack {

    public BigSlamma() {
        super("Big Slamma", 10);
    }

    public Pair<Integer, Stat> use() {
        int damage = getInfo()
        ;
        return new Pair<>(damage, null);
    }
}
