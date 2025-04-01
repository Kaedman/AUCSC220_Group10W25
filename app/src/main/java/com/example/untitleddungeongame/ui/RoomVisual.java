package com.example.untitleddungeongame.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.untitleddungeongame.animations.Sprite;

import java.util.Arrays;


/**
 * Half assleep programming + depression, lets go
 *
 *
 * Room Visual - Contains tile sprite index data for a indivdual room, as well as a visual bitmap to be drawn to canvas
 */
public class RoomVisual {
    public static Sprite tileVisuals;
    private Bitmap looks;
    private Canvas drawer;
    //Dimensions fo the room
    static private int tilesX = 12;
    static private int tilesY = 10;

    static private int tilePixelWidth = 32;
    static private int tilePixelHeight = 32;
    static private int floorTileIndex = 1;
    static private int emptyTile = -1;
    static private int scaleX = 4;
    static private int scaleY = 4;

    //Other
    private int[][] mapSpriteData;
    private int[] allowedTileVisuals;

    public RoomDirection roomDirecitionType;

    public RoomVisual(int[] allowedTileIndexs, RoomDirection directionType){
        mapSpriteData = new int[tilesY][tilesX];
        allowedTileVisuals = allowedTileIndexs;
        roomDirecitionType = directionType;

        looks = Bitmap.createBitmap(tilesX * tilePixelWidth * scaleX, tilesY * tilePixelHeight * scaleY, Bitmap.Config.ARGB_8888);
        drawer = new Canvas(looks);
        drawer.setBitmap(looks);

        setUpArray();
    }
    private void setUpArray(){
        for (int y = 0; y < mapSpriteData.length; y ++){
            Arrays.fill(mapSpriteData[y], emptyTile);
        }
        System.out.println("Done setup");
    }

    /**
     * Generates a basic room, where it creates 4 enterances into the room and a outline of tiles
     */
    public void generateBaseRoom(){
        //Fill vertical edges with sprites
        for (int i = 0; i < mapSpriteData.length; i ++){
            mapSpriteData[i][0] = getRandomSprite();
            mapSpriteData[i][mapSpriteData[i].length -1] = getRandomSprite();
        }
        for (int i = 0; i < mapSpriteData[0].length; i ++){
            mapSpriteData[0][i] = getRandomSprite();
        }
        for (int i = 0; i < mapSpriteData.length; i ++){
            mapSpriteData[mapSpriteData.length -1][i] = getRandomSprite();
        }
    }

    /**
     * Opens up enterances where they should be
     */
    private void fixEnterances(){
        switch(roomDirecitionType){

        }

    }

    private int getRandomSprite(){
        double random = Math.random();
        int arrayLength = allowedTileVisuals.length - 1;
        return (int) (arrayLength * random);

    }

    /**
     * Draws all sprites to the bitmap which will display the room
     * The sprite should be set before this
     */
    public void generateVisual(){
        drawer.setBitmap(looks);
        tileVisuals.setCurrentSprite(floorTileIndex);
        //Draw floor first, then actual tiles
        for (int yOffset = 0; yOffset <  mapSpriteData.length; yOffset++){
            for (int xOffset = 0; xOffset < mapSpriteData[yOffset].length; xOffset++){
                tileVisuals.drawScaled(drawer,xOffset * tilePixelWidth * scaleX, yOffset * tilePixelHeight * scaleY, scaleX, scaleY);

            }

        }


        //actual tiles
        for (int yOffset = 0; yOffset <  mapSpriteData.length; yOffset++){
            for (int xOffset = 0; xOffset < mapSpriteData[yOffset].length; xOffset++){
                System.out.println(mapSpriteData[yOffset][xOffset]);
                if (mapSpriteData[yOffset][xOffset] != emptyTile) {
                    tileVisuals.setCurrentSprite(mapSpriteData[yOffset][xOffset]);
//                    tileVisuals.setCurrentSprite(0);
                    tileVisuals.drawScaled(drawer, xOffset * tilePixelWidth * scaleX, yOffset * tilePixelHeight * scaleY, scaleX, scaleY);
                }
            }
        }

    }

    public void draw(Canvas c){
        Rect r = new Rect( 0, 0, tilesX * tilePixelWidth * scaleX, tilesY * tilePixelHeight * scaleY);
        c.drawBitmap(looks, r, r, null);

    }



}
