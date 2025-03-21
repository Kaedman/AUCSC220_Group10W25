package com.example.untitleddungeongame.items;

public interface Item {
    byte count = 0;

    public void use();

    public void drop(int count);


}
