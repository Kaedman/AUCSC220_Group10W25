package com.example.untitleddungeongame;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MyCallBack implements SurfaceHolder.Callback {

    private GameLoop game;
    private SurfaceView gameView;
    private AppCompatActivity activity;
    Thread gameThread;

    public MyCallBack(AppCompatActivity activity, SurfaceView gameView){
        this.gameView = gameView;
        this.activity = activity;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size); //Instead of returning a value, we need to specify a point variable to change

        System.out.printf("Width: %d, Height: %d\n", size.x, size.y);
        System.out.printf("Is Surface Valid: %b\n", holder.getSurface().isValid());

        game = new GameLoop(activity, holder, size);
        game.setDoGameLoop(true);

        gameThread = new Thread(game);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        //end code
        game.setDoGameLoop(false);
    }
}