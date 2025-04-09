package com.example.untitleddungeongame;

import android.graphics.Bitmap;

import java.util.HashMap;

public class Assets {
    public enum AssetID {
        //ENTITY
        PLAYER,

        ENEMY_SLIME,
        //MAP
        TILESET,
        CHESTS,
        //Items
        ITEM_HEAL,
        ITEM_OFFENSE,

        ITEM_SLOT,
        //UI
        BUTTON_PAUSE,
        BUTTON_INVENTORY,

        HEALTH_BAR,
        CONFIRM_ARROWS,
        DIALOG_FRAME
    }
    static HashMap<AssetID, Bitmap> assets = new HashMap<>();

    static public void addAsset(AssetID id, Bitmap bitmap) {
        assets.put(id, bitmap);
    }

    static public Bitmap getAsset(AssetID id) {
        return assets.get(id);
    }

}
