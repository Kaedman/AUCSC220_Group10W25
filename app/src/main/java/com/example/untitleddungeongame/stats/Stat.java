package com.example.untitleddungeongame.stats;

public class Stat {
    private StatType type;
    private int value;

    public Stat(StatType type, int value) {
        this.type = type;
        this.value = value;
    }

    public StatType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void updateValue(int value) {
        this.value = value;
    }
}
