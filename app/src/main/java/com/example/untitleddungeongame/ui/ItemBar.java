package com.example.untitleddungeongame.ui;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.untitleddungeongame.R;
import com.example.untitleddungeongame.items.Item;

public class ItemBar {
    public interface OnClick {
        void run(int pos);
    }
    Button[] itemButtons = new Button[4];
    LinearLayout itemBar;

    private OnClick onClick;
    private boolean disabled = false;
    private boolean show = true;
    byte count = 0;
    public ItemBar(AppCompatActivity activity) {
        itemBar = activity.findViewById( R.id.item_bar);
        getButtons();

        for (int i = 0; i < itemButtons.length; i++) {
            int finalI = i;
            itemButtons[i].setOnClickListener(v -> {
                if (onClick != null) {
                    onClick.run(finalI);
                }
            });
        }
    }

    private void getButtons() {
        itemButtons[0] = itemBar.findViewById(R.id.item1);
        itemButtons[1] = itemBar.findViewById(R.id.item2);
        itemButtons[2] = itemBar.findViewById(R.id.item3);
        itemButtons[3] = itemBar.findViewById(R.id.item4);
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public void displayButtons(boolean value) {
        if (show != value) {
            itemBar.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
            show = value;
        }
    }

    @SuppressLint("DefaultLocale")
    public void setItemButtons(Item[] items) {
        if (items.length == count) return;

        for (int i = 0; i < itemButtons.length; i++) {
            Button button = itemButtons[i];
            if (i < items.length) {
                Item item = items[i];
                if (item == null) {
                    button.setVisibility(View.INVISIBLE);
                    continue;
                }
                button.setText(String.format("%s x%d", item.getName(), item.getCount()));
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.INVISIBLE);
            }
        }
    }


}
