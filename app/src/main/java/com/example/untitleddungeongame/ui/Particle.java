package com.example.untitleddungeongame.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Telephony;

public class Particle {

    private int posX, posY;
    private int lifeTimer, lifeTimerMax;
    private float velocityX, velocityY;

    private float frictionX, frictionY;
    //Technical
    //Could use particles not purley for visuals, but for moving x or y points

    /**
     * Particle, allows some sort of bitmap visual to move accross the screen for a specified time
     * @param positionX - int intial X position
     * @param positionY - int initial Y position
     Velocity - Moves the at a specified rate
     * @param velocityX - float intial X velocity
     * @param velocityY - float intial Y velocity
     Friction - Slows down or speeds up the particle by multiplying friction to velocity each frame
     * @param frictionX - float X friction
     * @param frictionY - float Y friction
     * @param maxLifeTime - int frame amount that particles can live
     */
    public Particle(int positionX, int positionY, float velocityX, float velocityY, float frictionX, float frictionY, int maxLifeTime){
        posX = positionX; posY = positionY;
        this.velocityX = velocityX; this.velocityY = velocityY;
        this.frictionX = frictionX; this.frictionY = frictionY;
        lifeTimerMax = maxLifeTime;

    }

    public Particle(int positionX, int positionY, float velocityX, float velocityY, int maxLifeTime){
        this(positionX, positionY, velocityX, velocityY, 1, 1, maxLifeTime);
    }

    public Particle(int positionX, int positionY, int maxLifeTime){
        this(positionX, positionY, 0, 0, 1, 1, maxLifeTime);
    }

    public void update(){
        posX += (int)(frictionX * velocityX);
        posY += (int)(frictionY * velocityY);
    }

    //Actual visuals
    private Bitmap visual;
    int visualSizeX, visualSizeY;

    public void setVisual(Bitmap bitmap){
        visual = bitmap;
        visualSizeX = bitmap.getWidth();
        visualSizeY = bitmap.getHeight();
    }

    public void drawVisual(Canvas canvas, Paint p, int xDisplace, int yDisplace, int scaleX, int scaleY){
        Rect r = new Rect(0, 0, visualSizeX, visualSizeY);
        Rect toDraw = new Rect(posX + xDisplace, posY + yDisplace, (posX + xDisplace) + visualSizeX * scaleX, (posY + yDisplace) + visualSizeY * scaleY);

        canvas.drawBitmap(visual, r, toDraw, p);
    }

    public void drawVisual(Canvas canvas, Paint p, int scaleX, int scaleY){
        drawVisual(canvas, p, 0, 0, scaleX, scaleY);
    }

}
