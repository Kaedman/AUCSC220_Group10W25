package com.example.untitleddungeongame.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.R;

import java.util.Objects;

public class PixelButton  extends FrameLayout implements TextureView.SurfaceTextureListener {
    private SurfaceTexture surface;
    private FrameLayout rootElement;
    private TextureView surfaceView;
    private TextView textView;
    Bitmap buttonSprite;
    private final Point size = new Point(0, 0);
    private Button handlerButton;
    private float rotation = 0.0f;



    public PixelButton(Context context) {
        super(context);
        init(context);
    }

    public PixelButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PixelButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context) {
        inflate(context, R.layout.pixel_button, this);
        rootElement = findViewById(R.id.pixel_button_root);
        surfaceView = findViewById(R.id.pixel_button_frame);
        surfaceView.setSurfaceTextureListener(this);
        surfaceView.setOpaque(false);
        handlerButton = findViewById(R.id.pixel_button_handle);
        handlerButton.setAlpha(0.0f);
        textView = findViewById(R.id.pixel_text);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PixelButton);
        init(context);
        if (a.hasValue(R.styleable.PixelButton_resource)) {
            int resourceId = Integer.parseInt(Objects.requireNonNull(a.getString(R.styleable.PixelButton_resource)));
            // Load the bitmap from the resource ID
            buttonSprite = Assets.getAsset(resourceId);
        } else {
            buttonSprite = Assets.getAsset(Assets.AssetID.BUTTON);
        }

        if (a.hasValue(R.styleable.PixelButton_android_text)) {
            String text = a.getString(R.styleable.PixelButton_android_text);
            textView.setText(text);
        }

        if (a.hasValue(R.styleable.PixelButton_android_textColor)) {
            int color = a.getColor(R.styleable.PixelButton_android_textColor, Color.WHITE);
            textView.setTextColor(color);
        }

        if (a.hasValue(R.styleable.PixelButton_android_textStyle)) {
            String style = a.getString(R.styleable.PixelButton_android_textStyle);
            Typeface typeface = Typeface.create(style, Typeface.NORMAL);
            textView.setTypeface(typeface);
        }

//        if (a.hasValue(R.styleable.PixelButton_android_rotation)) {
//            this.rotation = a.getFloat(R.styleable.PixelButton_android_rotation, 0);
//        }

        a.recycle();
    }

    public void draw() {
        if (!surfaceView.isAvailable()) return;

        Canvas canvas = surfaceView.lockCanvas(); //get the current surface as a canvas object, prevent changes to surface
        if (canvas == null) return; //check canvas is correct
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
        canvas.save();

        size.x = canvas.getWidth();
        size.y = canvas.getHeight();
        RectF where = new RectF(0, 0, size.x, size.y);
        ColorMatrix colorMatrix = new ColorMatrix();
        boolean isPressed = handlerButton.isPressed();
        if (isPressed) colorMatrix.setSaturation(0.5f); // Set saturation to 0.5 for pressed state
        textView.setAlpha(isPressed ? 0.5f : 1.0f); // Set text alpha based on pressed state
        Paint paint = new Paint();
        paint.setColorFilter(new android.graphics.ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(buttonSprite, null, where, paint); //draw the bitmap to the canvas
//        canvas.rotate(rotation);
        canvas.restore();
        surfaceView.unlockCanvasAndPost(canvas); //unlock the canvas and post it to the surface
    }

    public void setButtonSprite(Bitmap bitmap) {
        buttonSprite = bitmap;
        draw();
    }


    public void setOnClickListener(OnClickListener listener) {
        handlerButton.setOnClickListener(listener);
    }
    public void setText(String text) {
        textView.setText(text);
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }

    public void setTextStyle(Typeface style) {
        textView.setTypeface(style);
    }

    public void disable(boolean disable) {
        rootElement.setVisibility(disable ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
        this.surface = surface;
        draw();
    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {
        // Handle size changes if needed
        draw();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {
        // Handle updates if needed
        draw();
    }
}
