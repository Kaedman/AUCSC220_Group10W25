package com.example.untitleddungeongame;

import android.view.MotionEvent;
import android.view.View;

import com.example.untitleddungeongame.handlers.Game;

//Getting location of on touch
//https://stackoverflow.com/questions/3476779/how-to-get-the-touch-position-in-android

public class GameTouchListener implements View.OnTouchListener {

    Game control;
    public GameTouchListener(Game gameLoop){
        control = gameLoop;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        control.onTouchEvent(event.getX(), event.getY());
        return false;
    }
}
