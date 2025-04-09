package com.example.untitleddungeongame.hotbar.items;

import com.example.untitleddungeongame.entity.Entity;
import com.example.untitleddungeongame.hotbar.HotBarInfo;

public abstract class Item extends HotBarInfo {
    private final String name;
    protected int count;
    private final int maxCount;

    public Item(String name, int maxCount) {
        super(name);
        this.maxCount = maxCount;
        this.name = name;
    }

    abstract public boolean use(int position, Entity character);

    public void drop(int amount, int position, Item[] storage) {
        count -= amount;
        if (count == 0) {
            remove(position, storage);
        }
    }

    protected void remove(int position, Item[] storage) {
        storage[position] = null;
    }

    public int getInfo() {
        return count;
    }

    public boolean add(int amount) {
        if (count + amount <= maxCount) { // If the amount is less than the max count
            count += amount;
            return true;
        }
        return false;
    }

    public boolean equals(Item item) {
        return name.equals(item.getName()) && count == item.getInfo();
    }

    public boolean equals(String name, int count) {
        return this.name.equals(name) && this.count == count;
    }

}
