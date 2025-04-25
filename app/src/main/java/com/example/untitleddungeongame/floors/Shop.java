package com.example.untitleddungeongame.floors;

import androidx.appcompat.app.AppCompatActivity;

import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.hotbar.items.Item;
import com.example.untitleddungeongame.hotbar.items.heals.Apple;
import com.example.untitleddungeongame.hotbar.items.heals.Potion;

import java.util.ArrayList;

public class Shop extends Room {
    private ArrayList<Item> shopItems;
    private final int MAX_ITEMS = 4;
    private final int SELECTABLE_ITEMS = 2;

    public Shop(int roomId) {
        super(roomId, null);
        this.shopItems = new ArrayList<>();
    }

    private void generateItems(int itemsToAdd) {
        while (shopItems.size() < itemsToAdd || shopItems.size() < MAX_ITEMS) {
            int randItem = (int) (Math.random() * SELECTABLE_ITEMS);

            switch(randItem) {
                case 0:
                    shopItems.add(new Apple());
                    break;
                case 1:
                    shopItems.add(new Potion());
                    break;
            }
        }
    }
}
