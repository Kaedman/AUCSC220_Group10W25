package com.example.untitleddungeongame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;






public class GameLoop extends SurfaceView implements Runnable {
    //Refering to this tutorial: https://gamecodeschool.com/android/coding-a-snake-game-for-android/

    //Game Control
    private boolean doGameLoop;
    private boolean isPaused;
    private int fps;

    //Graphics
    private Canvas canvas; //drawing happens here
    private SurfaceHolder surfaceHolder; //Actual visual

    private Paint paint; //This may not be required
    private int screenX;
    private int screenY;

    private SurfaceView  viewToDrawOn;
    Bitmap bitmap;

    //Other
    private Context context;

    public GameLoop(Context context) {
        super(context);

        this.context = context;
        //Stuff happens Here

        fps = 1000/60;

        surfaceHolder = getHolder();
        paint = new Paint();

    }
    public GameLoop(Context context,  Point size){
        this(context);

        fps = 1000/60;

        screenX = size.x;
        screenY = size.y;

        paint = new Paint();
    }

    public void initalizeRest(){
        //Other initalizations that need to happen after on create
//        surfaceHolder = viewToDrawOn.getHolder();

        surfaceHolder = getHolder();

        System.out.println(surfaceHolder.getSurface().isValid());
    }

    @Override
    public void run() {
        initalizeRest();
        //GameLoop happens Here
        while (doGameLoop){
//            System.out.println("Running loop");
            draw();

            try {
                Thread.sleep(fps);
            }
            catch (InterruptedException e){
                //error
            }
        }

    }



    public void setDoGameLoop(boolean state){
        doGameLoop = state;
    }


    public void setSurfaceHolder(SurfaceHolder holder){
        surfaceHolder = holder;
    }

    /*
    Draw instructions for all visuals relevant to the game
     */
    public void draw(){
//        System.out.println(surfaceHolder.getSurface().isValid());
        if (!surfaceHolder.getSurface().isValid()) return;//check surface is correct
        //currently we are stopping at this check, as our surface is not valid
        //TODO: Get a proper working surface
        //https://stackoverflow.com/questions/42213818/android-surface-is-not-valid ?

        canvas = surfaceHolder.lockCanvas(); //get the current surface as a canvas object, prevent changes to surface

        //stuff happens
        System.out.println("Drawing");
        paint.setColor(Color.RED);
        canvas.drawRect(0,0,100,100, paint);


        surfaceHolder.unlockCanvasAndPost(canvas); //update the surface
        System.out.println("Drawing done");


    }

}
