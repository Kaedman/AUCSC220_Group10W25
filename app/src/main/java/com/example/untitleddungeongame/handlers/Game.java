package com.example.untitleddungeongame.handlers;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.GameTouchListener;
import com.example.untitleddungeongame.R;
import com.example.untitleddungeongame.animations.AnimatedSprite;
import com.example.untitleddungeongame.animations.Animation;
import com.example.untitleddungeongame.animations.Sprite;
import com.example.untitleddungeongame.handlers.combat.Combat;
import com.example.untitleddungeongame.misc.ElapseTime;
import com.example.untitleddungeongame.ui.CustomDialog;
import com.example.untitleddungeongame.misc.OutputText;
import com.example.untitleddungeongame.ui.RoomVisual;

public class Game extends SurfaceView implements Runnable {
    //Refering to this tutorial: https://gamecodeschool.com/android/coding-a-snake-game-for-android/

    //Game Control
    private boolean doGameLoop;
    public static boolean isPaused;
    public static boolean userPaused;
    private final int fps;

    //Graphics
    private Canvas canvas; //drawing happens here
    private SurfaceHolder surfaceHolder; //Actual visual
    protected final CustomDialog dialogBox;

    private Paint paint;
    private final Paint fill; //https://stackoverflow.com/questions/36717782/how-to-fill-canvas-with-a-color
    //Used for "refreshing" a canvas
    public static int screenX, screenY;

    //Used for adaptive scaling. Testing on the given screen resolution,
    //Canvas should scale down or up respectivly
    private final int SCREENX_CONST = 1440;
    private final int SCREENY_CONST = 3120;
    private float scaleX, scaleY;

    private SurfaceView  viewToDrawOn;
    Bitmap bitmap;
    Combat combat;


    //Other
    private final AppCompatActivity context;


    //Other
    private GameTouchListener touchListener;
    Sprite test;
    Animation animationTest;
    AnimatedSprite player;
    @SuppressLint("SetTextI18n")

    public Game(AppCompatActivity context, SurfaceHolder surfaceHolder, Point size, View gameView){
        super(context);
        this.context = context;
        this.surfaceHolder = surfaceHolder;

        fps = 1000/60;

        screenX = size.x;
        screenY = size.y;
        paint = new Paint();
        dialogBox = context.findViewById(R.id.combat_dialog);

        combat = new Combat(context);
        combat.setCombat(true);

        scaleX = (float) screenX / SCREENX_CONST;
        scaleY = (float) screenY / SCREENY_CONST;
        System.out.println(scaleX +  ", " + scaleY);
        //Fixed screen Scaling on smaller devices
        this.surfaceHolder.setFixedSize((SCREENX_CONST),(SCREENY_CONST)); //This fixed the scaling issue for smaller devices


        paint = new Paint();
        fill = new Paint();
        fill.setStyle(Paint.Style.FILL);
        fill.setColor(Color.BLACK);

        touchListener = new GameTouchListener(this);
        gameView.setOnTouchListener(touchListener);

        DrawInstructions.phoneSizeX = screenX;
        DrawInstructions.phoneSizeY = screenY;
        DrawInstructions.clearDrawList();

        //Pausing
        isPaused = false; //pausing controlled by leaving app, etc.
        userPaused = false; //Pausing controlled by pause button

    }

    RoomVisual roomVisual;
    Sprite tiles;
    public void testRoomVisuals(){
        roomVisual = new RoomVisual(new int[] {7,8,9});
        tiles = new Sprite(Assets.AssetID.TILESET, 32, 48, 13);
        RoomVisual.tileVisuals = tiles;
        roomVisual.generateBaseRoom();
        roomVisual.setEntrances(true, true, true, true);
        roomVisual.fixEntrances();
        roomVisual.generateVisual();

    }

    @Override
    public void run() {
        test = new Sprite(Assets.AssetID.PLAYER, 32, 32, 4);

        animationTest = new Animation("Idle", 0, 4, new int[]{84, 84, 124, 400});
        animationTest.setRepeat(true);
        animationTest.startAnimation();

        player = new AnimatedSprite(test);
        player.addAnimation(new Animation("idle", 0, 4, new int[] {400, 84, 124, 84}));
        player.setCurrentAnimation("idle");
        player.setCurrentRepeat(true);

        player.addAnimation(new Animation("static", 0, 0, new int[1]));
        player.setCurrentAnimation("idle");

        player.playCurrentAnimation();

        AnimatedSprite slimeTestAnim = new AnimatedSprite(new Sprite(Assets.AssetID.ENEMY_SLIME, 32, 32, 9));
        slimeTestAnim.addAnimation(new Animation("idle", 0, 9, new int[] {150, 94, 74, 94, 300, 94, 74, 94, 150}));
        slimeTestAnim.setCurrentAnimation("idle");
        slimeTestAnim.setCurrentRepeat(true);
        slimeTestAnim.playCurrentAnimation();

        DrawInstructions slimeInstruction = new DrawInstructions(0, 500, slimeTestAnim, 20, 20);

        testRoomVisuals();


        //GameLoop happens Here
        while (doGameLoop){
            if (!isPaused && !userPaused) {
                try {
                    draw();
                }
                catch (Error e){
//                    isPaused = true; //Surface seems to be not available, meaning it either changed or was destroyed
                    //Due to user likley exiting the app momentarly
                    System.out.println("I broke :(");
                }
                try {
                    Thread.sleep(fps);
                }
                catch (InterruptedException e) {
                    //error
                }

            }

        }

    }

    public void setDoGameLoop(boolean state){
        doGameLoop = state;
    }

    public void setSurfaceHolder(SurfaceHolder holder){
        surfaceHolder = holder;
    }

    /**
    Draw instructions for all visuals relevant to the game
     */
    /*
    Bug: Canvas Scaling is wierd on different sized devices, causing sprites to inflate and loss
    of onscreen canvas information
    Solution: made surface holder a fixed size of original development resolution, forcing it to not scale
    on smaller or bigger devices
    Fixed on: 2025-03-19
     */
    @SuppressLint("SetTextI18n")
    public void draw(){

        if (!surfaceHolder.getSurface().isValid()) return;//check surface is correct
        canvas = surfaceHolder.lockCanvas(); //get the current surface as a canvas object, prevent changes to surface
        ElapseTime.update(); // Update the current time
        //Drawing
        canvas.drawPaint(fill); //Refresh the canvas

        roomVisual.draw(canvas, (int)(-RoomVisual.getScaleX() * RoomVisual.getTilePixelWidth() * 0.5), 0);

        paint.setColor(Color.RED);
        test.setCurrentSprite(animationTest.updateFrame());
        test.drawScaled(canvas, paint,300, 400, 4, 4);

        player.updateCurrentAnimation();
        player.drawAnimation(canvas, 600, 200, 20, 20);
        combat.run();
        context.runOnUiThread(this::runOnUiThread);

        int pHP = combat.getPlayer().getHealth();
        int pMAXHP = combat.getPlayer().getMaxHealth();

        drawHealthBar(canvas, 600, 900, pHP, pMAXHP, 300, 30);

        DrawInstructions.drawAll(canvas);

        //Final Image updates

        surfaceHolder.unlockCanvasAndPost(canvas); //update the surface
    }

    public void onTouchEvent(float touchX, float touchY){
        if (dialogBox.isTextFinishedUpdating()) {
            dialogBox.closeDialog();
            OutputText.setInDialog(false);
        }
        combat.screenTappedOn(touchX, touchY);
    }

    public void onPause(){
        userPaused = true;
    }

    public void onQuit(){

    }
    public void setDialogText(String text) {
        dialogBox.setText(text);
        OutputText.setInDialog(true);
    }

    private void runOnUiThread() {
        dialogBox.updateText();
        if (OutputText.isNewText()) {
            setDialogText(OutputText.getOutputText());
        }
    }

    public void onResume(){
        userPaused = false;
    }


    /**
     * Helper for visualization of hp
     * @param c - canvas
     * @param x - positionX
     * @param y - positionY
     * @param currentHP - Current Hp of entity
     * @param maxHP - Max hp of entity
     */
    private void drawHealthBar(Canvas c, int x, int y, int currentHP, int maxHP, int barWidth, int barHeight){
        float currentPercent = ((float) currentHP/ (float) maxHP);

        Paint p = new Paint();
        p.setColor(Color.RED);
        c.drawRect(x, y, x + barWidth, y + barHeight, p);
        p.setColor(Color.GREEN);
        c.drawRect(x, y, x + barWidth * currentPercent, y + barHeight, p);
    }

    public void setRoomVisual(RoomVisual roomVisual) {
        this.roomVisual = roomVisual;
    }
}
