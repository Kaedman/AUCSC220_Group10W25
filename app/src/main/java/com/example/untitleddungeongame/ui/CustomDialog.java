package com.example.untitleddungeongame.ui;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.untitleddungeongame.R;
import com.example.untitleddungeongame.misc.ElapseTime;
import com.example.untitleddungeongame.misc.OutputText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomDialog extends LinearLayout {

    private LinearLayout rootView;
    private FrameLayout wrapperView;
    private TextView textView;
    private TextView popupInfo;

    private List<String> currentText = new ArrayList<>();
    private int length = 0;
    private int currantWordIndex = 0;
    private final ElapseTime elapseTime = new ElapseTime();
    private boolean isTextSet = false;
    private boolean autoClose = false;

    public CustomDialog(Context context) {
        super(context);
        init(context);
    }

    public CustomDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    @SuppressLint("SetTextI18n")
    private void init(Context context) {
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        LayoutInflater.from(context).inflate(R.layout.custom_dialog, this, true);
        rootView = findViewById(R.id.dialog_root);
        wrapperView = findViewById(R.id.dialog_wrapper);
        textView = findViewById(R.id.dialog_text);
        popupInfo = findViewById(R.id.dialog_info);
        popupInfo.setText("Tap anywhere on the screen");

        closeDialog();
    }

    public void setText(String text) {
        currentText = Arrays.asList(text.split(" "));
        length = text.length();
        currantWordIndex = 0;
        isTextSet = true;
        elapseTime.reset();
        rootView.setVisibility(VISIBLE);
    }

    public void updateText() {
        if (!isTextSet) return;
        if (length == 0) {
            elapseTime.reset();
            return;
        }

        String current = textView.getText().toString();

        if (current.length() == length) {
            elapseTime.reset();
            return;
        }

        if (!elapseTime.hasTimeElapsed(10)) return;

        String currentWord = currentText.get(currantWordIndex);
        String newText = String.format("%s %s", current, currentWord);
        OutputText.textUpdating();
        textView.setText(newText);
        if (newText.length() < length) {
            currantWordIndex++;
        } else {
            length = 0;
            isTextSet = false;
            OutputText.textFinished();
        }
    }

    public void closeDialog() {
        textView.setText("");
        rootView.setVisibility(GONE);
        elapseTime.reset();
    }

    public boolean isTextFinishedUpdating() {
        return !isTextSet;
    }
}
