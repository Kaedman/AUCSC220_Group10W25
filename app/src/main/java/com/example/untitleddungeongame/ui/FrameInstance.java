package com.example.untitleddungeongame.ui;

import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.animations.Sprite;

public class FrameInstance implements SurfaceHolder.Callback {
    public SurfaceHolder surfaceHolder;
    public Sprite frameSprite;
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        holder.setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder = holder;
        frameSprite = new Sprite(Assets.AssetID.ITEM_SLOT, 64, 64, 1);
        draw();
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

    public void draw() {
        if (!surfaceHolder.getSurface().isValid()) return;//check surface is correct
        Canvas canvas = surfaceHolder.lockCanvas(); //get the current surface as a canvas object, prevent changes to surface
        // Draw on the canvas here
        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
        frameSprite.drawScaled(canvas, 20, 20, 4, 4);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

}
