package com.example.untitleddungeongame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.HashMap;

public class GameLoop extends SurfaceView implements Runnable {
    //Refering to this tutorial: https://gamecodeschool.com/android/coding-a-snake-game-for-android/

    //Game Control
    private boolean doGameLoop;
    private boolean isPaused;
    private int fps;

    //Graphics
    private Canvas canvas; //drawing happens here
    private SurfaceHolder surfaceHolder; //Actual visual

    private Paint paint;
    private Paint fill; //https://stackoverflow.com/questions/36717782/how-to-fill-canvas-with-a-color
    //Used for "refreshing" a canvas
    private int screenX;
    private int screenY;

    private SurfaceView  viewToDrawOn;
    Bitmap bitmap;

    private HashMap<String, Bitmap> assets;

    //Other
    private Context context;
    private GameTouchListener touchListener;


    Sprite test;
    Animation animationTest;
    AnimatedSprite player;



    public GameLoop(Context context, SurfaceHolder surfaceHolder, Point size, View gameView){
        super(context);
        this.context = context;
        this.surfaceHolder = surfaceHolder;

        fps = 1000/60;

        screenX = size.x;
        screenY = size.y;


        paint = new Paint();
        fill = new Paint();
        fill.setStyle(Paint.Style.FILL);
        fill.setColor(Color.BLACK);

        touchListener = new GameTouchListener(this);
        gameView.setOnTouchListener(touchListener);
    }

    @Override
    public void run() {



        test = new Sprite(assets.get("playerRouge"), 32, 32, 4);

        animationTest = new Animation("Idle", 0, 4, new int[] {84, 84, 124, 400});
        animationTest.setRepeat(true);
        animationTest.startAnimation();

        player = new AnimatedSprite(test);
        player.addAnimation(new Animation("idle", 0, 4, new int[] {400, 84, 124, 84}));
        player.setCurrentAnimation("idle");
        player.setCurrentRepeat(true);

        player.addAnimation(new Animation("static", 0, 0, new int[1]));
        player.setCurrentAnimation("idle");

        player.playCurrentAnimation();




        //GameLoop happens Here
        while (doGameLoop){

            draw();

            try {
                Thread.sleep(fps);
            }
            catch (InterruptedException e){
                //error
            }
            doGameLoop = true; //REMOVE LATER //TODO REMOVE WHEN DONE TESTING
        }

    }

    public void setDoGameLoop(boolean state){
        doGameLoop = state;
    }

    public void setSurfaceHolder(SurfaceHolder holder){
        surfaceHolder = holder;
    }

    public void setAssets(HashMap assets) { this.assets = assets; }

    /*
    Draw instructions for all visuals relevant to the game
     */
    public void draw(){

        if (!surfaceHolder.getSurface().isValid()) return;//check surface is correct


        canvas = surfaceHolder.lockCanvas(); //get the current surface as a canvas object, prevent changes to surface

        //stuff happens

        canvas.drawPaint(fill); //Refresh the canvas

        paint.setColor(Color.RED);
//        canvas.drawRect(0,0,100,100, paint); //Temp Red square to make sure we did not screw up




//        test.drawSprite(canvas, paint,100, 100, 500, 500);
//        test.drawScaled(canvas, 300, 200, 3, 3);

        test.setCurrentSprite(animationTest.updateFrame());
        test.drawScaled(canvas, 300, 400, 4, 4);

        player.updateCurrentAnimation();
        player.drawAnimation(canvas, 600, 200, 20, 20);

        //TODO: Scale Canvas down to phone size, as it is currently not doing so right now

        surfaceHolder.unlockCanvasAndPost(canvas); //update the surface



    }

    public void onTouchEvent(float touchX, float touchY){
        System.out.println("Touch at : " + touchX + ", " + touchY);
    }

}
