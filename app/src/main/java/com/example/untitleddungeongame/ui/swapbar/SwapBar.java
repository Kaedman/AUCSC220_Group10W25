package com.example.untitleddungeongame.ui.swapbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.untitleddungeongame.R;
import com.example.untitleddungeongame.hotbar.HotBarInfo;
import com.example.untitleddungeongame.hotbar.items.Item;
import com.example.untitleddungeongame.ui.swapbar.SwapBarOption;

public class SwapBar<T extends Item> extends LinearLayout {
    public interface OnClick {
        void run(int index, Item item);
    }
    private LinearLayout rootView;
    private final SwapBarOption<T>[] swapBarOptions = new SwapBarOption[4];
    private OnClick onClick;
    private T[] listOfElements;


    public SwapBar(Context context) {
        super(context);
        init(context);
    }

    public SwapBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.modular_swapbar, this, true);
        rootView = findViewById(R.id.item_bar_root);
        getButtons();

        for (int i = 0; i < swapBarOptions.length; i++) {
            int finalI = i;
            swapBarOptions[i].setOnClick(v -> {
                if (onClick != null) {
                    onClick.run(finalI, (Item) listOfElements[finalI]);
                }
            });
        }
    }
    private void getButtons() {
        swapBarOptions[0] = rootView.findViewById(R.id.item_bar_1);
        swapBarOptions[1] = rootView.findViewById(R.id.item_bar_2);
        swapBarOptions[2] = rootView.findViewById(R.id.item_bar_3);
        swapBarOptions[3] = rootView.findViewById(R.id.item_bar_4);
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
        for (int i = 0; i < swapBarOptions.length; i++) {
            swapBarOptions[i].setItem(listOfElements[i]);
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
