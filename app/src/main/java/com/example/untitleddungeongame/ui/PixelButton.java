package com.example.untitleddungeongame.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.untitleddungeongame.R;

public class PixelButton  extends ConstraintLayout implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private ConstraintLayout rootElement;
    private SurfaceView surfaceView;
    private Button handlerButton;
    public PixelButton(Context context) {
        super(context);
        init(context);
    }

    public PixelButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PixelButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.pixel_button, this);
        rootElement = findViewById(R.id.pixel_button_root);
        handlerButton = findViewById(R.id.pixel_button_handle);
        surfaceView = findViewById(R.id.pixel_button_frame);
        surfaceView.getHolder().addCallback(this);
        surfaceView.setZOrderOnTop(true);
        surfaceView.setZOrderMediaOverlay(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        holder.setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder = holder;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        holder.setFormat(PixelFormat.TRANSLUCENT);
        // Handle surface changes if needed
        surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // Handle surface destruction if needed
        surfaceHolder = null;
    }
}
