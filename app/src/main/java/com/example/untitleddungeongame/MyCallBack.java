package com.example.untitleddungeongame;


import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.untitleddungeongame.floors.RoomMaster;
import com.example.untitleddungeongame.handlers.Game;

public class MyCallBack implements SurfaceHolder.Callback {

    private Game game;
    private SurfaceView gameView;
    private AppCompatActivity activity;
    Thread gameThread;
    private RoomMaster roomMaster;

    public MyCallBack(AppCompatActivity activity, SurfaceView gameView){
        this.gameView = gameView;
        this.activity = activity;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (gameView != null) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size); //Instead of returning a value, we need to specify a point variable to change

            System.out.printf("Width: %d, Height: %d\n", size.x, size.y);
            System.out.printf("Is Surface Valid: %b\n", holder.getSurface().isValid());

            game = new Game(activity, holder, size, gameView);
            game.setDoGameLoop(true);
            roomMaster.setGame(game);
            game.setRoomVisual(roomMaster.getCurrentRoom().getLooks());
            //Log.d("looks", )

            gameThread = new Thread(game);
            gameThread.start();
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

    public Game getGame() {
        return game;
    }

    public void setRoomMaster(RoomMaster roomMaster) {
        this.roomMaster = roomMaster;
    }
}