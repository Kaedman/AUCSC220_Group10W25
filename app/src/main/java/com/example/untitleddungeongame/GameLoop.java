package com.example.untitleddungeongame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private SurfaceHolder surfaceHolder; //Actual visual

    private Paint paint; //This may not be required

    //Other
    private Context context;

    public GameLoop(Context context) {
        super(context);

        this.context = context;
        //Stuff happens Here

        surfaceHolder = getHolder();
        paint = new Paint();

    }
    public GameLoop(Context context, SurfaceView surfaceView){
        this(context);
        surfaceHolder = surfaceView.getHolder();

    }

    @Override
    public void run() {
        //GameLoop happens Here

        System.out.println("Running loop");
        draw();
    }

    /*
    Draw instructions for all visuals relevant to the game
     */
    public void draw(){

        if (!surfaceHolder.getSurface().isValid()) return;//check surface is correct
        //currently we are stopping at this check, as our surface is not valid
        //TODO: Get a proper working surface

        canvas = surfaceHolder.lockCanvas(); //get the current surface as a canvas object, prevent changes to surface

        //stuff happens
        System.out.println("Drawing");
        paint.setColor(Color.RED);
        canvas.drawRect(0,0,100,100, paint);


        surfaceHolder.unlockCanvasAndPost(canvas); //update the surface
        System.out.println("Drawing done");


    }

}
