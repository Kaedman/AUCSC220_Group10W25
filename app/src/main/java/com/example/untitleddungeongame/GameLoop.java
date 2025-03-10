package com.example.untitleddungeongame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameLoop extends SurfaceView implements Runnable {
    //Refering to this tutorial: https://gamecodeschool.com/android/coding-a-snake-game-for-android/

    //Game Control
    boolean doGameLoop;
    boolean isPaused;


    //Graphics
    private Canvas canvas; //drawing happens here
    private SurfaceHolder actualSurface; //Actual visual

    private Paint paint; //This may not be required

    public GameLoop(Context context) {
        super(context);
        //Stuff happens Here

        actualSurface = getHolder();

    }

    @Override
    public void run() {
        //GameLoop happens Here

    }

    /*
    Draw instructions for all visuals relevant to the game
     */
    public void draw(){
        if (!actualSurface.getSurface().isValid()) return;//check surface is correct

        canvas = actualSurface.lockCanvas(); //get the current surface as a canvas object, prevent changes to surface
        //stuff happens


        actualSurface.unlockCanvasAndPost(canvas); //update the surface



    }



}
