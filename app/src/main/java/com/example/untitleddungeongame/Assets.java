package com.example.untitleddungeongame;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

public class Assets {
    public enum AssetID {
        //ENTITY
        PLAYER(0),
        ENEMY_SLIME(1),
        //MAP
        TILESET(2),
        CHESTS(3),
        //Items
        ITEM_HEAL(4),
        ITEM_OFFENSE(5),

        ITEM_SLOT(6),
        //UI
        BUTTON_PAUSE(7),
        BUTTON_INVENTORY(8),

        HEALTH_BAR(9),
        CONFIRM_ARROWS(10),
        BUTTON(11),
        BUTTON_PRESSED(12),
        DIALOG_FRAME(13),
        GHASTLY_SHOPKEEPER(14),
        ENEMY_GOBLIN(15),
        ENEMY_BIG_GOBLIN(16),
        BENCH(17),
        MONEY_ICON(18),
        MAP(19),
        TORCH(20);

        final int id;

        AssetID(int i) {
            this.id = i;
        }
    }
    static HashMap<AssetID, Bitmap> assets = new HashMap<>();

    static public void addAsset(AssetID id, Bitmap bitmap) {
        assets.put(id, bitmap);
    }

    static public Bitmap getAsset(AssetID id) {
        return assets.get(id);
    }
    static public Bitmap getAsset(int id) {
        for (AssetID assetID : AssetID.values()) {
            if (assetID.id == id) {
                return assets.get(assetID);
            }
        }
        return null;
    }

    static public void importAssets(Resources resources) {
        //TODO: Migrate keys and image values to a json or xml file, then loop through to create assets
        Assets.addAsset(Assets.AssetID.PLAYER, BitmapFactory.decodeResource(resources, R.drawable.playerrouge));
        Assets.addAsset(Assets.AssetID.ENEMY_SLIME, BitmapFactory.decodeResource(resources, R.drawable.enemyslime));
        Assets.addAsset(Assets.AssetID.TILESET, BitmapFactory.decodeResource(resources, R.drawable.tiles));
        Assets.addAsset(Assets.AssetID.CHESTS, BitmapFactory.decodeResource(resources, R.drawable.chests)); //chests may be updated to show opened state
        Assets.addAsset(Assets.AssetID.ITEM_HEAL, BitmapFactory.decodeResource(resources, R.drawable.itemsheals));
        Assets.addAsset(Assets.AssetID.ITEM_OFFENSE, BitmapFactory.decodeResource(resources, R.drawable.itemsoffense));
        Assets.addAsset(Assets.AssetID.ITEM_SLOT, BitmapFactory.decodeResource(resources, R.drawable.itemslot));
        Assets.addAsset(Assets.AssetID.BUTTON, BitmapFactory.decodeResource(resources, R.drawable.button));
        Assets.addAsset(Assets.AssetID.BUTTON_PRESSED, BitmapFactory.decodeResource(resources, R.drawable.buttonpressed));
        Assets.addAsset(Assets.AssetID.BUTTON_PAUSE, BitmapFactory.decodeResource(resources, R.drawable.buttonpause));
        Assets.addAsset(Assets.AssetID.BUTTON_INVENTORY, BitmapFactory.decodeResource(resources, R.drawable.buttoninventory));
        Assets.addAsset(Assets.AssetID.DIALOG_FRAME, BitmapFactory.decodeResource(resources, R.drawable.dialog_frame));
        Assets.addAsset(Assets.AssetID.HEALTH_BAR, BitmapFactory.decodeResource(resources, R.drawable.healthbar));
        Assets.addAsset(Assets.AssetID.CONFIRM_ARROWS, BitmapFactory.decodeResource(resources, R.drawable.arrow));
        Assets.addAsset(Assets.AssetID.GHASTLY_SHOPKEEPER, BitmapFactory.decodeResource(resources, R.drawable.gastlyshopkeeper_sheet));
        Assets.addAsset(Assets.AssetID.ENEMY_GOBLIN, BitmapFactory.decodeResource(resources, R.drawable.goblin));
        Assets.addAsset(Assets.AssetID.ENEMY_BIG_GOBLIN, BitmapFactory.decodeResource(resources, R.drawable.orceboss_sheet));
        Assets.addAsset(Assets.AssetID.BENCH, BitmapFactory.decodeResource(resources, R.drawable.bench));
        Assets.addAsset(Assets.AssetID.MONEY_ICON, BitmapFactory.decodeResource(resources, R.drawable.moneyicon_sheet));
        Assets.addAsset(Assets.AssetID.MAP, BitmapFactory.decodeResource(resources, R.drawable.minimapbutton));
    }
}
