package com.example.untitleddungeongame.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;

import com.example.untitleddungeongame.handlers.Game;

import java.util.HashMap;

public class MapVisuals {

    private HashMap<String, Drawable> currentDrawables;
    public int cameraX, cameraY; //Pixel location of where to draw everything
    public int mapSizeX, mapSizeY; //Max Room ARRAY dimensions
    public int currentPosX, currentPosY; //Current Room ARRAY dimensions
    public final int TILE_X = 32;
    public final int TILE_Y = 48;
    final int roomTileX = 10;
    final int roomTileY = 12;
    public Bitmap baseView;
    public Canvas drawnView;

    public MapVisuals(int maxX, int maxY){
        cameraX = 0; cameraY = 0;
        resetHash();

        mapSizeX = maxX; mapSizeY = maxY;
        currentPosX = 0; currentPosY = 0;

        baseView = Bitmap.createBitmap(maxX * TILE_X, maxY * TILE_Y, Bitmap.Config.ARGB_8888); //Actual Visual
        drawnView = new Canvas(baseView);
    }
    public void resetHash(){
        currentDrawables = new HashMap<String, Drawable>();
    }

    public void loadFloor1Assets(){
        currentDrawables.put("4Way", Drawable.createFromPath("app/src/main/RoomResources/Floor1/4Ways/MapBase.png"));
        currentDrawables.put("NoRight", Drawable.createFromPath("app/src/main/RoomResources/Floor1/3Ways/NoRight.png"));
        currentDrawables.put("NoDown", Drawable.createFromPath("app/src/main/RoomResources/Floor1/3Ways/NoDown.png"));
        currentDrawables.put("NoLeft", Drawable.createFromPath("app/src/main/RoomResources/Floor1/3Ways/NoLeft.png"));
        currentDrawables.put("NoUp", Drawable.createFromPath("app/src/main/RoomResources/Floor1/3Ways/NoUp.png"));
        currentDrawables.put("DR", Drawable.createFromPath("app/src/main/RoomResources/Floor1/Corners/DR.png"));
        currentDrawables.put("LD", Drawable.createFromPath("app/src/main/RoomResources/Floor1/Corners/LD.png"));
        currentDrawables.put("UL", Drawable.createFromPath("app/src/main/RoomResources/Floor1/Corners/UL.png"));
        currentDrawables.put("RU", Drawable.createFromPath("app/src/main/RoomResources/Floor1/Corners/RU.png"));
        currentDrawables.put("EntranceD", Drawable.createFromPath("app/src/main/RoomResources/Floor1/Corners/EntranceD.png"));
        currentDrawables.put("EntranceL", Drawable.createFromPath("app/src/main/RoomResources/Floor1/Corners/EntranceL.png"));
        currentDrawables.put("EntranceR", Drawable.createFromPath("app/src/main/RoomResources/Floor1/Corners/EntranceR.png"));
        currentDrawables.put("EntranceU", Drawable.createFromPath("app/src/main/RoomResources/Floor1/Corners/EntranceU.png"));
        currentDrawables.put("UD", Drawable.createFromPath("app/src/main/RoomResources/Floor1/Corners/EntranceU.png"));
        currentDrawables.put("LR", Drawable.createFromPath("app/src/main/RoomResources/Floor1/Hallways/UpDown/MapBase.png"));
        //Fail to get path
    }

    /**
     * Updates the bitmap
     */
    public void paintBitmap(){
        //Reminder, There is a 1 tile gap edge on each image
//        currentDrawables.get("UL").draw(drawnView);
//        drawnView.setBitmap(baseView);

//        drawnView.drawBitmap(test, 0,0, null);


    }

    public void drawToCanvas(Canvas c){
        c.drawBitmap(baseView, new Rect(0, 0, Game.screenX, Game.screenY), new Rect(0, 0, Game.screenX, Game.screenY), null);


    }


}
