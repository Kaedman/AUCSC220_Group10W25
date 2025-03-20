package com.example.untitleddungeongame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import android.graphics.RectF;
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
    private int screenX, screenY;

    //Used for adaptive scaling. Testing on the given screen resolution,
    //Canvas should scale down or up respectivly
    private final int SCREENX_CONST = 1440;
    private final int SCREENY_CONST = 3120;
    private float scaleX, scaleY;

    private SurfaceView  viewToDrawOn;
    Bitmap bitmap;
    CombatMechanic combat;

    private final TextView playerHealth;
    private final TextView enemyHealth;

    //Other
    private final AppCompatActivity context;
    @SuppressLint("SetTextI18n")
    



    public GameLoop(Context context, SurfaceHolder surfaceHolder, Point size, View gameView){
        super(context);
        this.context = context;
        this.surfaceHolder = surfaceHolder;

        fps = 1000/60;

        screenX = size.x;
        screenY = size.y;
        private HashMap<String, Bitmap> assets;

        //Other
        private Context context;
        private GameTouchListener touchListener;


        Sprite test;
        Animation animationTest;
        AnimatedSprite player;
        paint = new Paint();

        Button itemsButton = context.findViewById(R.id.items_button);
        Button attackButton = context.findViewById(R.id.attack_button);
        playerHealth = context.findViewById(R.id.player_health);
        enemyHealth = context.findViewById(R.id.enemy_health);

        attackButton.setText("Attack");
        itemsButton.setText("Items");

        combat = new CombatMechanic(attackButton, itemsButton);

        combat.setCombat(true);
        scaleX = (float) screenX / SCREENX_CONST;
        scaleY = (float) screenY / SCREENY_CONST;
        System.out.println(scaleX +  ", " + scaleY);
        Sprite.globalScaleX = scaleX; //Remove??
        Sprite.globalScaleY = scaleY;
        //Fixed screen Scaling on smaller devices
        this.surfaceHolder.setFixedSize((SCREENX_CONST),(SCREENY_CONST)); //This fixed the scaling issue for smaller devices


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

            try { Thread.sleep(fps); }
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
    public void draw(){

        if (!surfaceHolder.getSurface().isValid()) return;//check surface is correct

        canvas = surfaceHolder.lockCanvas(); //get the current surface as a canvas object, prevent changes to surface
        //Drawing
        canvas.drawPaint(fill); //Refresh the canvas

        paint.setColor(Color.RED);


//        canvas.drawRect(0,0,100,100, paint); //Temp Red square to make sure we did not screw up
//        test.drawSprite(canvas, paint,100, 100, 500, 500);
//        test.drawScaled(canvas, 300, 200, 3, 3);

        test.setCurrentSprite(animationTest.updateFrame());
        test.drawScaled(canvas, paint,300, 400, 4, 4);

        player.updateCurrentAnimation();
        player.drawAnimation(canvas, 600, 200, 20, 20);
        if (combat.isInCombat) {
              combat.runCombat();
              context.runOnUiThread(() -> {
                  playerHealth.setText("pH: " + combat.player.health);
                  enemyHealth.setText("eH: " + combat.enemy.health);
              });
         }


        //Final Image updates

        surfaceHolder.unlockCanvasAndPost(canvas); //update the surface
    }

    public void onTouchEvent(float touchX, float touchY){
        System.out.println("Touch at : " + touchX + ", " + touchY);
    }

}
