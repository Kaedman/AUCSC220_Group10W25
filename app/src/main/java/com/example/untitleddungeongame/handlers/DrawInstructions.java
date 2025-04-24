package com.example.untitleddungeongame.handlers;

import android.graphics.Canvas;
import android.util.Log;

import com.example.untitleddungeongame.animations.AnimatedSprite;
import com.example.untitleddungeongame.animations.Sprite;

import java.util.ArrayList;

/**
 * Instructions for handling anyhting drawing related, this includes camera displacement and drawing
 * all sprites to the screen.
 */
public class DrawInstructions {
    //Cameras: used for displacing all sprites and calculating whehter an animation should draw
    public static int cameraX = 0;
    public static int cameraY = 0;
    public static int phoneSizeX;
    public static int phoneSizeY;


    private int x, y;
    private int scaleX, scaleY;
    private int spriteSizeX, spriteSizeY;
    private static ArrayList<DrawInstructions> thingsToDraw = new ArrayList<DrawInstructions>();

    public AnimatedSprite spriteToDraw;

    public boolean show = true; //To be changed publicly, allows changing of sprite visibility

    /**
     * Draw instruction basic information needed
     * IMPORTANT: Be sure to initalize the static phone size first before drawing!!!!
     *
     * @param positionX - int position where the sprite should be located horizontally
     * @param positionY - int position where the sprite should be located vertically
     * @param animatedSprite - Already made animation holder
     * @param scaleX - int scale Amount horizontally
     * @param scaleY - int scale Amount Vertically
     */
    public DrawInstructions(int positionX, int positionY, AnimatedSprite animatedSprite, int scaleX, int scaleY){
        x = positionX;
        y = positionY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;

        spriteToDraw = animatedSprite;

        thingsToDraw.add(this);

        updateSpriteSize();

    }

    private void updateSpriteSize(){
        Sprite s = spriteToDraw.getSprite();

        spriteSizeX = s.getSpriteX();
        spriteSizeY = s.getSpriteY();
    }


    /*
    Draws all existing animated sprites if they are on screen
     */
    public static void drawAll(Canvas canvas, boolean isEnemy){
        DrawInstructions current;
        for (int index = 0; index < thingsToDraw.size(); index ++){
            current = thingsToDraw.get(index);

//            System.out.println(current.boundsCheckLeft() + ", " + current.boundsCheckRight() + ", " + current.boundsCheckUp() + ", " + current.boundsCheckDown());
            //Check sprite is in screen bounds to draw
            if (current.spriteToDraw.doDraw) {
                if (current.boundsCheckLeft() && current.boundsCheckRight() && current.boundsCheckUp() && current.boundsCheckDown()) {
                    current.spriteToDraw.updateCurrentAnimation();
                    current.spriteToDraw.drawAnimation(canvas, current.x + cameraX, current.y + cameraY, current.scaleX, current.scaleY);
                }
            }
        }
        //Complexity of 4n
    }

    //pos + size = either right or bottom size of sprite
    private boolean boundsCheckLeft(){
        return ((x + spriteSizeX * scaleX) > cameraX);
    }
    private boolean boundsCheckRight(){
        return (x < cameraX + phoneSizeX);
    }
    private boolean boundsCheckUp(){
        return ((y + spriteSizeY * scaleY) > cameraY);
    }
    private boolean boundsCheckDown(){
        return (y < cameraY + phoneSizeY);
    }

    public static void clearDrawList(){
        thingsToDraw = new ArrayList<DrawInstructions>();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setScaleY(int scaleY) {
        this.scaleY = scaleY;
    }

    public void setScaleX(int scaleX) {
        this.scaleX = scaleX;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
