package com.example.untitleddungeongame.ui.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.untitleddungeongame.R;
import com.example.untitleddungeongame.hotbar.HotBarInfo;
import com.example.untitleddungeongame.ui.FrameInstance;

public class HotBarOption<T extends HotBarInfo> extends FrameLayout {
    private FrameLayout rootView;
    private TextView itemName;
    private TextView itemCount;
    private Button itemButton;
    private SurfaceView itemFrame;
    private Runnable onClick;

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
        itemName = findViewById(R.id.item_name);
        itemCount = findViewById(R.id.item_count);
        itemButton = findViewById(R.id.item_touch);
        itemFrame = findViewById(R.id.item_frame);
        itemFrame.getHolder().addCallback(new FrameInstance());

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
        itemName.setText(currentName);
        itemCount.setText(String.format("x%s", currentCount));
        disable(false);
    }

    public void disable(boolean value) {
        if (value) {
            itemButton.setVisibility(GONE);
            itemName.setText("");
            itemCount.setText("");
        } else {
            itemButton.setEnabled(true);
            itemButton.setAlpha(1f);
        }

    }

    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
        itemButton.setOnClickListener(v -> {
            if (onClick != null) {
                onClick.run();
            }
        });
    }
}
