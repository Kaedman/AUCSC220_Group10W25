package com.example.untitleddungeongame.floors;

import android.util.Log;

import com.example.untitleddungeongame.hotbar.items.Item;
import com.example.untitleddungeongame.hotbar.items.heals.Apple;
import com.example.untitleddungeongame.hotbar.items.heals.Potion;

import java.util.ArrayList;

public class Shop extends Room {
    //shopItems is stored in an ArrayList for easy addition/removal, converted to an array when set
    // to the shopBar
    private Item[] shopItems;
    private final int MAX_ITEMS = 4;
    private final int SELECTABLE_ITEMS = 2;

    public Shop(int roomId) {
        super(roomId, null);
        this.shopItems = new Item[4];
    }

    public void generateItems(int itemsToAdd) {
        ArrayList<Integer> addedList = new ArrayList<>();
        for (int i = 0; i < itemsToAdd; i++) {
            Item newItem;

            int randItem = (int) (Math.random() * SELECTABLE_ITEMS);

            switch(randItem) {
                case 0:
                    newItem = new Apple();
                    break;
                case 1:
                    newItem = new Potion();
                    break;
                default:
                    newItem = new Potion();
                    break;
            }

            if (addedList.size() + 1 > MAX_ITEMS) {
                Log.d("Item not Added", "Item not added, too many items!");
            }

            if (!addedList.contains(randItem)) {
                newItem.add(((int) (Math.random() * 3)) + 1);

                shopItems[i] = newItem;
            }

            addedList.add(randItem);
        }
    }

    public Item[] getShopItems() {
        return shopItems;
    }

    public void removeItem(int index) {
        if (shopItems[index].getInfo() - 1 <= 0) {
            shopItems[index] = null;
        } else {
            shopItems[index].setCount(shopItems[index].getInfo() - 1);
        }
    }
}
