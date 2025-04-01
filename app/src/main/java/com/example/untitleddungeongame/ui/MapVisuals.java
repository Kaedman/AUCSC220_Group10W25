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


    public void drawToCanvas(Canvas c){
        c.drawBitmap(baseView, new Rect(0, 0, Game.screenX, Game.screenY), new Rect(0, 0, Game.screenX, Game.screenY), null);


    }


}
