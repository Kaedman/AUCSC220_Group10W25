package com.example.untitleddungeongame.ui.hotbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import com.example.untitleddungeongame.R;
import com.example.untitleddungeongame.hotbar.HotBarInfo;

public class HotBar<T extends HotBarInfo> extends LinearLayout {
    public interface OnClick {
        void run(int pos);
    }
    private LinearLayout rootView;
    private final HotBarOption<T>[] hotBarOptions = new HotBarOption[4];
    private OnClick onClick;
    T[] listOfElements;


    public HotBar(Context context) {
        super(context);
        init(context);
    }

    public HotBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.modular_hotbar, this, true);
        rootView = findViewById(R.id.item_bar_root);
        getButtons();

        for (int i = 0; i < hotBarOptions.length; i++) {
            int finalI = i;
            hotBarOptions[i].setOnClick(_v -> {
                if (onClick != null) {
                    onClick.run(finalI);
                }
            });
        }
    }
    private void getButtons() {
        hotBarOptions[0] = rootView.findViewById(R.id.item_bar_1);
        hotBarOptions[1] = rootView.findViewById(R.id.item_bar_2);
        hotBarOptions[2] = rootView.findViewById(R.id.item_bar_3);
        hotBarOptions[3] = rootView.findViewById(R.id.item_bar_4);
    }
    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }
    public void set(T[] elements) {
        this.listOfElements = elements;
    }
    public void updateUi() {
        if (listOfElements == null) {
            return;
        }
        for (int i = 0; i < hotBarOptions.length; i++) {
            hotBarOptions[i].setItem(listOfElements[i]);
        }
    }

    public void disable(boolean disable) {
        if (disable) {
            rootView.setVisibility(GONE);
        } else {
            rootView.setVisibility(VISIBLE);
        }
    }
}
