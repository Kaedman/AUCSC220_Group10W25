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

        surfaceHolder = getHolder();
        paint = new Paint();

    }
    public GameLoop(Context context, SurfaceView surfaceView, Point size){
        this(context);

        screenX = size.x;
        screenY = size.y;

        viewToDrawOn = surfaceView;
        bitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);


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

        System.out.println("Running loop");
//        draw();
        draw2();
    }

    /*
    Draw instructions for all visuals relevant to the game
     */
    public void draw(){

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

    //https://gamecodeschool.com/android/drawing-graphics-demo/
    public void draw2(){
        System.out.println("Drawing");
        paint.setColor(Color.RED);
        canvas.drawRect(0,0,100,100, paint);
        viewToDrawOn.draw(canvas);

    }

}
