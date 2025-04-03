package com.example.untitleddungeongame.items;

import com.example.untitleddungeongame.characters.Entity;

public abstract class Item {
    private final String name;
    protected int count;
    private final int maxCount;

    public Item(String name, int maxCount) {
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

    public String getName() {
        return name;
    }

    protected void remove(int position, Item[] storage) {
        storage[position] = null;
    }

    public int getCount() {
        return count;
    }

    public boolean add(int amount) {
        if (count + amount <= maxCount) { // If the amount is less than the max count
            count += amount;
            return true;
        }
        return false;
    }


}
