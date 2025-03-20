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

    CombatMechanic combat;

    private final TextView playerHealth;
    private final TextView enemyHealth;

    //Other
    private final AppCompatActivity context;
    @SuppressLint("SetTextI18n")
    public GameLoop(AppCompatActivity context, SurfaceHolder surfaceHolder, Point size){
        super(context);
        this.context = context;
        this.surfaceHolder = surfaceHolder;

        fps = 1000/60;

        screenX = size.x;
        screenY = size.y;

        paint = new Paint();

        Button itemsButton = context.findViewById(R.id.items_button);
        Button attackButton = context.findViewById(R.id.attack_button);
        playerHealth = context.findViewById(R.id.player_health);
        enemyHealth = context.findViewById(R.id.enemy_health);

        attackButton.setText("Attack");
        itemsButton.setText("Items");

        combat = new CombatMechanic(attackButton, itemsButton);

        combat.setCombat(true);
    }

    public void initalizeRest(){
        //Other initalizations that need to happen after on create

        System.out.println(surfaceHolder.getSurface().isValid());
    }



    @Override
    public void run() {
        initalizeRest();
        //GameLoop happens Here
        while (doGameLoop){
//            System.out.println("Running loop");
            draw();

            try { Thread.sleep(fps); }
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
    @SuppressLint("SetTextI18n")
    public void draw(){

        if (!surfaceHolder.getSurface().isValid()) return;//check surface is correct
        canvas = surfaceHolder.lockCanvas(); //get the current surface as a canvas object, prevent changes to surface

        if (combat.isInCombat) {
            combat.runCombat();
            context.runOnUiThread(() -> {
                playerHealth.setText("pH: " + combat.player.health);
                enemyHealth.setText("eH: " + combat.enemy.health);
            });
        }

        surfaceHolder.unlockCanvasAndPost(canvas); //update the surface
    }

}
