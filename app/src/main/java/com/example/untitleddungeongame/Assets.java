package com.example.untitleddungeongame;

import android.graphics.Bitmap;

import java.util.HashMap;

public class Assets {
    public enum AssetID {
        //ENTITY
        PLAYER,
        GHASTLY_SHOPKEEPER,

        ENEMY_SLIME,
        ENEMY_GOBLIN,
        ENEMY_BIG_GOBLIN,
        //MAP
        TILESET,
        CHESTS,
        BENCH,
        //Items
        ITEM_HEAL,
        ITEM_OFFENSE,

        ITEM_SLOT,
        //UI
        BUTTON_PAUSE,
        BUTTON_INVENTORY,
        MONEY_ICON,
        HEALTH_BAR,
        CONFIRM_ARROWS,
        BUTTON, BUTTON_PRESSED, DIALOG_FRAME
    }
    static HashMap<AssetID, Bitmap> assets = new HashMap<>();

    static public void addAsset(AssetID id, Bitmap bitmap) {
        assets.put(id, bitmap);
    }

    static public Bitmap getAsset(AssetID id) {
        return assets.get(id);
    }

}
