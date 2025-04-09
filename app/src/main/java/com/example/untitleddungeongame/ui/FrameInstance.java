package com.example.untitleddungeongame.ui;

import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

public class FrameInstance implements SurfaceHolder.Callback {
    public SurfaceHolder surfaceHolder;
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        surfaceHolder = holder;
        // Initialize the drawing thread or any other setup her
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes if needed
        surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

}
