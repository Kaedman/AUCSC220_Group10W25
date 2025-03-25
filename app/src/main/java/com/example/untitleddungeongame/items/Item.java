package com.example.untitleddungeongame.items;

public interface Item {
    int count = 0; //Should discuss with group if wanting to be able to carry more than 1 item in a slot
    //Or only 1 item? If 1 item, why couldn't we just remove item from invetory after use (unless this tracks it)

    public void use();

    public void drop(int count);


}
