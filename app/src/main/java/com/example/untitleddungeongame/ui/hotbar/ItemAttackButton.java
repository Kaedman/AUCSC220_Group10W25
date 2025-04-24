package com.example.untitleddungeongame.ui.hotbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.untitleddungeongame.R;

public class ItemAttackButton extends ConstraintLayout {
    private ConstraintLayout rootLayout;
    private ImageView attackButton;
    private ImageView itemButton;
    private boolean isDisabled = false;

    public ItemAttackButton(Context context) {
        super(context);
        init(context);
    }

    public ItemAttackButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemAttackButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.attack_items, this);
        rootLayout = findViewById(R.id.itemAttackButton);
        attackButton = findViewById(R.id.attack_button);
        itemButton = findViewById(R.id.items_button);
    }

    public void setAttackButtonClickListener(OnClickListener listener) {
        attackButton.setOnClickListener(listener);
    }

    public void setItemButtonClickListener(OnClickListener listener) {
        itemButton.setOnClickListener(listener);
    }

    public void  disable(boolean disable) {
        if (disable == isDisabled) return;
        if (disable) {
            rootLayout.setVisibility(GONE);
        } else {
            rootLayout.setVisibility(VISIBLE);
        }
        isDisabled = disable;
    }
}
