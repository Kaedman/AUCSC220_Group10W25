package com.example.untitleddungeongame.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Particle {

    private int posX, posY;
    private int lifeTimer, lifeTimerMax;
    private float velocityX, velocityY;

    private float velocityChangeX, velocityChangeY;

    public boolean alive;
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
        this.velocityChangeX = frictionX; this.velocityChangeY = frictionY;
        lifeTimerMax = maxLifeTime;
        alive = true;

    }

    public Particle(int positionX, int positionY, float velocityX, float velocityY, int maxLifeTime){
        this(positionX, positionY, velocityX, velocityY, 1, 1, maxLifeTime);
    }

    public Particle(int positionX, int positionY, int maxLifeTime){
        this(positionX, positionY, 0, 0, 0, 0, maxLifeTime);
    }

    public void update(){

        alive = (lifeTimer <= lifeTimerMax);

        if (alive) {
            lifeTimer ++;
            posX += velocityX;
            posY -= velocityY;
            velocityY += velocityChangeY;
            velocityX += velocityChangeX;

        }

    }

    public void setCurrentLifeTime(int newTime){
        lifeTimer = newTime;
    }
    public void setMaxLifeTime(int newMax){
        lifeTimerMax = newMax;
    }


    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void setVelocityChangeX(float velocityChangeX) {
        this.velocityChangeX = velocityChangeX;
    }

    public void setVelocityChangeY(float velocityChangeY) {
        this.velocityChangeY = velocityChangeY;
    }

    //Actual visuals
    private Bitmap visual;
    int visualSizeX, visualSizeY;

    public void setVisualBitmap(Bitmap bitmap){
        visual = bitmap;
        visualSizeX = bitmap.getWidth();
        visualSizeY = bitmap.getHeight();
    }

    /**
     * Creates a rectangle visual
     * @param rect - rect for visual
     * @param color - color of rect
     */
    public void setVisualRect(Rect rect, int color){
        visual = Bitmap.createBitmap(rect.right, rect.bottom, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(visual);
        c.setBitmap(visual);

        Paint p = new Paint();
        p.setColor(color);
        c.drawRect(rect, p);
    }


    public void drawVisual(Canvas canvas, Paint p, int xDisplace, int yDisplace, int scaleX, int scaleY){
        Rect r = new Rect(0, 0, visualSizeX, visualSizeY);
        Rect toDraw = new Rect(posX + xDisplace, posY + yDisplace, (posX + xDisplace) + visualSizeX * scaleX, (posY + yDisplace) + visualSizeY * scaleY);

//        canvas.drawBitmap(visual, r, toDraw, p);
        canvas.drawBitmap(visual, posX + xDisplace, posY + yDisplace, null);
    }

    public void drawVisual(Canvas canvas, Paint p, int scaleX, int scaleY){
        drawVisual(canvas, p, 0, 0, scaleX, scaleY);
    }

}
