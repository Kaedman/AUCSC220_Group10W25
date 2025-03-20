package com.example.untitleddungeongame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MyCallBack implements SurfaceHolder.Callback {

    private GameLoop game;
    private SurfaceView gameView;
    private AppCompatActivity activity;
    Thread gameThread;

    private HashMap<String, Bitmap> assets;

    public MyCallBack(AppCompatActivity activity, SurfaceView gameView, HashMap assets){
        this.gameView = gameView;
        this.activity = activity;
        this.assets = assets;

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (gameView != null) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size); //Instead of returning a value, we need to specify a point variable to change

            System.out.printf("Width: %d, Height: %d\n", size.x, size.y);
            System.out.printf("Is Surface Valid: %b\n", holder.getSurface().isValid());

            game = new GameLoop(activity, holder, size, gameView);
            game.setDoGameLoop(true);
            game.setAssets(assets);

            gameThread = new Thread(game);
            gameThread.start();

            game.isPaused = false;
        }
        else{
            game.isPaused = false;
            game.setSurfaceHolder(gameView.getHolder());
        }


    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        //end code
        game.setDoGameLoop(false);
    }

    /*
    Allows for external control of the game
     */
    public GameLoop getGame(){
        return game;
    }

    public void reStartGame(){
        gameThread.start();
        System.out.println("Starting Up");
    }
}