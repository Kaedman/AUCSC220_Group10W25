package com.example.untitleddungeongame.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import com.example.untitleddungeongame.Floors.RoomMaster;
import com.example.untitleddungeongame.R;

public class Arrows extends androidx.constraintlayout.widget.ConstraintLayout {
    private ConstraintLayout rootView;
    private ImageView leftArrow;
    private ImageView rightArrow;
    private ImageView upArrow;
    private ImageView downArrow;
    private RoomMaster roomMaster;

    public Arrows(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Arrows(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.arrow_container, this, true);
        rootView = findViewById(R.id.arrow_container);
        leftArrow = findViewById(R.id.left_arrow);
        rightArrow = findViewById(R.id.right_arrow);
        upArrow = findViewById(R.id.up_arrow);
        downArrow = findViewById(R.id.down_arrow);
    }

    public void setArrowActivated(ImageView v, boolean activated) {
        Log.d("arrowId", v.getResources().getResourceName(v.getId()));
        if (activated) {
            v.setEnabled(true);

            switch (v.getResources().getResourceName(v.getId())) {
                case "com.example.untitleddungeongame:id/left_arrow":
                    v.setImageResource(R.drawable.confirmationarrow3);
                    break;
                case "com.example.untitleddungeongame:id/right_arrow":
                    v.setImageResource(R.drawable.confirmationarrow1);
                    break;
                case "com.example.untitleddungeongame:id/up_arrow":
                    v.setImageResource(R.drawable.confirmationarrow4);
                    break;
                case "com.example.untitleddungeongame:id/down_arrow":
                    v.setImageResource(R.drawable.confirmationarrow2);
                    break;
            }
        } else {
            v.setEnabled(false);

            switch (v.getResources().getResourceName(v.getId())) {
                case "com.example.untitleddungeongame:id/left_arrow":
                    v.setImageResource(R.drawable.shadedarrow3);
                    break;
                case "com.example.untitleddungeongame:id/right_arrow":
                    v.setImageResource(R.drawable.shadedarrow1);
                    break;
                case "com.example.untitleddungeongame:id/up_arrow":
                    v.setImageResource(R.drawable.shadedarrow4);
                    break;
                case "com.example.untitleddungeongame:id/down_arrow":
                    v.setImageResource(R.drawable.shadedarrow2);
                    break;
            }
        }
    }

    public void setArrows() {
        setArrowActivated(leftArrow, roomMaster.getCurrentRoom().getLeft() != null);

        setArrowActivated(rightArrow, roomMaster.getCurrentRoom().getRight() != null);

        setArrowActivated(upArrow, roomMaster.getCurrentRoom().getUp() != null);

        setArrowActivated(downArrow, roomMaster.getCurrentRoom().getDown() != null);
    }

    public void moveLeft(View v) {
        setArrowActivated(leftArrow,false);
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getLeft());
    }

    public void moveRight(View v) {
        setArrowActivated(rightArrow,false);
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getRight());
    }

    public void moveUp(View v) {
        setArrowActivated(upArrow,false);
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getUp());
    }

    public void moveDown(View v) {
        setArrowActivated(downArrow,false);
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getDown());
    }


    public void setRoomMaster(RoomMaster roomMaster) {
        this.roomMaster = roomMaster;
    }
}
