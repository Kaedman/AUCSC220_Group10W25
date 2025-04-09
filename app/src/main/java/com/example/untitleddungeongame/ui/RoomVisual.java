package com.example.untitleddungeongame.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.untitleddungeongame.animations.AssetID;
import com.example.untitleddungeongame.animations.Sprite;

import java.util.Arrays;
import java.util.Random;


/**
 * Half assleep programming + depression, lets go
 *
 *
 * Room Visual - Contains tile sprite index data for a indivdual room, as well as a visual bitmap to be drawn to canvas
 */
public class RoomVisual {
    public static Sprite tileVisuals = new Sprite(Assets.AssetID.TILESET), 32, 48, 13);;
    private Bitmap looks;
    private Canvas drawer;
    //Dimensions fo the room
    static private int tilesX = 7;
    static private int tilesY = 14;

    static private int tilePixelWidth = 32;
    static private int tilePixelHeight = 32;
    static private int floorTileIndex = 1;
    static private int emptyTile = -1;
    static private int scaleX = 7;
    static private int scaleY = 7;
    static private Random random = new Random();
    //Enterances
    public boolean entranceUp = false;
    public boolean entranceLeft = false;
    public boolean entranceRight = false;
    public boolean entranceDown = false;

    //Other
    private int[][] mapSpriteData;
    private int[] allowedTileVisuals;



    public RoomVisual(int[] allowedTileIndexs){
        mapSpriteData = new int[tilesY][tilesX];
        allowedTileVisuals = allowedTileIndexs;


        looks = Bitmap.createBitmap(tilesX * tilePixelWidth * scaleX, tilesY * tilePixelHeight * scaleY, Bitmap.Config.ARGB_8888);
        drawer = new Canvas(looks);
        drawer.setBitmap(looks);

        setUpArray();
    }
    private void setUpArray(){
        for (int y = 0; y < mapSpriteData.length; y ++){
            Arrays.fill(mapSpriteData[y], emptyTile);
        }

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
        for (int i = 0; i < mapSpriteData[0].length; i ++){
            mapSpriteData[mapSpriteData.length -1][i] = getRandomSprite();
        }
    }

    public void setEntrances(boolean up, boolean left, boolean right, boolean down){
        entranceUp = up;
        entranceLeft = left;
        entranceRight = right;
        entranceDown = down;
    }

    /**
     * Opens up enterances where they should be
     */
    public void fixEntrances(){
        //Calculate Edge Midpoints
        int verticalEdgeMidpoint = mapSpriteData.length/2;
        int horizontalEdgeMidpoint = mapSpriteData[0].length/2;
        //Ease of access + reading
        int top = 0;
        int bottom = mapSpriteData.length -1;
        int left = 0;
        int right = mapSpriteData[0].length -1;

        if (entranceUp){
            mapSpriteData[top][horizontalEdgeMidpoint] = -1;
        }
        if (entranceLeft){
            mapSpriteData[verticalEdgeMidpoint][left] = -1;
        }
        if (entranceRight){
            mapSpriteData[verticalEdgeMidpoint][right] = -1;
        }
        if (entranceDown){
            mapSpriteData[bottom][horizontalEdgeMidpoint] = -1;
        }

    }

    private int getRandomSprite(){
        return allowedTileVisuals[random.nextInt(allowedTileVisuals.length)];
        //https://stackoverflow.com/questions/5034370/retrieving-a-random-item-from-arraylist
        //Getting random element from list, I was using math.Random before
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

                if (mapSpriteData[yOffset][xOffset] != emptyTile) {
                    tileVisuals.setCurrentSprite(mapSpriteData[yOffset][xOffset]);
//                    tileVisuals.setCurrentSprite(0);
                    tileVisuals.drawScaled(drawer, xOffset * tilePixelWidth * scaleX, yOffset * tilePixelHeight * scaleY, scaleX, scaleY);
                }
            }
        }

    }

    public void draw(Canvas c, int xOffset, int yOffset){
        Rect canvasPaintLocation = new Rect(xOffset, yOffset, tilesX * tilePixelWidth * scaleX, tilesY * tilePixelHeight * scaleY);
        Rect bitmapGrab = new Rect(0, 0, tilesX * tilePixelWidth * scaleX, tilesY * tilePixelHeight * scaleY);
        c.drawBitmap(looks, bitmapGrab, canvasPaintLocation, null);

    }

    public static int getTilesX() {
        return tilesX;
    }

    public static int getTilesY() {
        return tilesY;
    }

    public static int getTilePixelWidth() {
        return tilePixelWidth;
    }

    public static int getTilePixelHeight() {
        return tilePixelHeight;
    }

    public static int getScaleX() {
        return scaleX;
    }

    public static int getScaleY() {
        return scaleY;
    }
}