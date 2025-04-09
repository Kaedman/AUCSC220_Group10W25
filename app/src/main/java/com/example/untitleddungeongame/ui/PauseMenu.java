package com.example.untitleddungeongame.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import com.example.untitleddungeongame.R;


public class PauseMenu extends LinearLayout {
    private LinearLayout rootView;
    private Button quitButton;
    private Button resumeButton;
    public PauseMenu(Context context) {
        super(context);
    }

    public PauseMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.pause_menu, this, true);
        rootView = findViewById(R.id.pause_menu);
        quitButton = findViewById(R.id.quit_button);
        resumeButton = findViewById(R.id.resume_button);
    }

    public void setOnResumeClickListener(OnClickListener listener) {
        resumeButton.setOnClickListener(listener);
    }

    public void setOnQuitClickListener(OnClickListener listener) {
        quitButton.setOnClickListener(listener);
    }

    public void disable(boolean disable) {
        if (disable) {
            rootView.setVisibility(GONE);
        } else {
            rootView.setVisibility(VISIBLE);
        }
    }
}
