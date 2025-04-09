package com.example.untitleddungeongame.animations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.untitleddungeongame.Assets;

public class Sprite {
    public Bitmap resource;

    private Rect currentBound;
    private Rect canvasPosition;

    private int spriteX;
    private int spriteY;

    private int collumnAmount;
    private int currentIndex;

    public Sprite(Assets.AssetID id, int spriteWidth, int spriteHeight, int numberOfCollumns){

        resource = Assets.getAsset(id);

        spriteX = spriteWidth;
        spriteY = spriteHeight;
        collumnAmount = numberOfCollumns;
        currentIndex = 0;

        currentBound = new Rect(0, 0, spriteX, spriteY);
        canvasPosition = new Rect(0, 0, spriteX, spriteY);

    }

    /*
    Draws the sprite at the current index, stretching it between the two points given with a given paint (scaling)
     */
    public void drawSprite(Canvas canvas, Paint paint, int posX, int posY, int scaleX, int scaleY){

        canvasPosition.left = posX;
        canvasPosition.top = posY;
        canvasPosition.right = scaleX;
        canvasPosition.bottom = scaleY;

        canvas.drawBitmap(resource, currentBound, canvasPosition, paint);

//        canvas.drawBitmap(resource, posX, posY, paint); // This works

    }
    /*
    Draws the sprite at the current index, stretching it between the pos and scale points
    No paint specified --> Nearest Neighbor Scaling (Preferred for game)
     */
    public void drawSprite(Canvas canvas, int posX, int posY, int scaleX, int scaleY){
        drawSprite(canvas, null, posX, posY, scaleX, scaleY);
    }

    /*
    Draws the current sprite at a specified index and scale amount (eg. 2x scale x and y)
     */
    public void drawScaled(Canvas canvas, Paint paint, int posX, int posY, int scaleX, int scaleY){
        canvasPosition.set(posX, posY, posX + spriteX * scaleX, posY + spriteY* scaleY);

        canvas.drawBitmap(resource, currentBound, canvasPosition, paint);


    }
    /*
    Draws the current sprite at a specified index and scale amount (eg. 2x scale x and y)
    Nearest Neighbor scaling
     */
    public void drawScaled(Canvas canvas, int posX, int posY, int scaleX, int scaleY){
        drawScaled(canvas, null, posX, posY, scaleX, scaleY);
    }

    public void drawBitmapScaled(Canvas canvas, Bitmap bitmap, int posX, int posY, int scaleX, int scaleY){
        Bitmap updatedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(updatedBitmap);
        c.setBitmap(updatedBitmap);
        c.drawBitmap(bitmap, 0, 0, null);
        drawScaled(c, posX, posY, scaleX, scaleY);

        canvas = c;
        bitmap = updatedBitmap;
    }

    //TODO: Flip Sprite (for both horizontal and vertical axis)


    private void setCurrentIndex(int index){
        currentIndex = index;

    }

    //TODO: Test with sprites that may have more than 1 row.
    private void updateSprite(){
        int offsetY = 0;
        int index = currentIndex;
        //Correct the choosen position by offsetting the y position bound
        while (index > collumnAmount){
            index -= collumnAmount;
            offsetY++;


        }

        currentBound.set(index * spriteX, offsetY * spriteY, spriteX + index * spriteX, spriteY + offsetY * spriteY);
        /*
        Bug: Sprite draws only on inital frame.
        FIX: Incorrect right and bottom specified, was just sprite X and spriteY, but needed a position greater
            Than left and top
        Details: March 14, 2025. By Cole Monnich
         */

    }

    /*
    Updates the current sprite AND the sprite looks
     */
    public void setCurrentSprite(int index){
        setCurrentIndex(index);
        updateSprite();
    }

    public int getSpriteY() {
        return spriteY;
    }

    public int getSpriteX() {
        return spriteX;
    }
}
