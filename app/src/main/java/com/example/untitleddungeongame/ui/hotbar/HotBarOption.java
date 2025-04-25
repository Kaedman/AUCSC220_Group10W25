package com.example.untitleddungeongame.ui.hotbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.R;
import com.example.untitleddungeongame.hotbar.HotBarInfo;
import com.example.untitleddungeongame.ui.FrameInstance;
import com.example.untitleddungeongame.ui.PixelButton;

public class HotBarOption<T extends HotBarInfo> extends ConstraintLayout {
    private ConstraintLayout rootView;
    private TextView itemCount;
    private PixelButton itemFrame;

    private int currentCount = 0;
    private String currentName = "";

    public HotBarOption(Context context) {
        super(context);
        init(context);
    }

    public HotBarOption(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.container, this);
        rootView = findViewById(R.id.item_root);
        itemCount = findViewById(R.id.item_count);
        itemFrame = findViewById(R.id.item_frame);
        itemFrame.setButtonSprite(Assets.getAsset(Assets.AssetID.ITEM_SLOT));
        disable(true);
    }

    public void setItem(T item) {
        if (item == null) {
            disable(true);
            return;
        }
        if (item.equals(currentName, currentCount)) {
            return;
        }
        currentCount = item.getInfo();
        currentName = item.getName();
        itemFrame.setText(currentName);
        itemCount.setText(String.format("x%s", currentCount));
        disable(false);
    }

    public void disable(boolean value) {
        if (value) {
            itemFrame.setVisibility(GONE);
            itemCount.setText("");
        } else {
            itemFrame.setVisibility(VISIBLE);
        }
    }

    public void setOnClick(OnClickListener onClick) {
        itemFrame.setOnClickListener(onClick);
    }
}
