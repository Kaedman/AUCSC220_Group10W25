package com.example.untitleddungeongame.hotbar;

public abstract class HotBarInfo {
    public final String name;

    protected HotBarInfo(String name) {
        this.name = name;
    }

    abstract public int getInfo();
    public String getName() {
        return name;
    }
    public boolean equals(String name, int count) {
        return this.name.equals(name) && this.getInfo() == count;
    }
}
