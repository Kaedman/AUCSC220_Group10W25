package com.example.untitleddungeongame.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.untitleddungeongame.floors.RoomMaster;
import com.example.untitleddungeongame.R;

public class Arrows extends androidx.constraintlayout.widget.ConstraintLayout {
    private ConstraintLayout rootView;
    private PixelButton leftArrow;
    private PixelButton rightArrow;
    private PixelButton upArrow;
    private PixelButton downArrow;
    private RoomMaster roomMaster;
    private boolean isDisabled = false;

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
        leftArrow.setOnClickListener(this :: moveLeft);
        rightArrow = findViewById(R.id.right_arrow);
        rightArrow.setOnClickListener(this :: moveRight);
        upArrow = findViewById(R.id.up_arrow);
        upArrow.setOnClickListener(this :: moveUp);
        downArrow = findViewById(R.id.down_arrow);
        downArrow.setOnClickListener(this :: moveDown);
    }

    public void setArrowVisible(PixelButton v, boolean activated) {
        if (activated) {
            v.setVisibility(VISIBLE);
        } else {
            v.setVisibility(GONE);
        }
    }

    /**
     * Shows or hides the arrows depending on whether or not the currentRoom has associated
     * neighbouring rooms
     */
    public void setArrows() {
        if (roomMaster == null) return;
        setArrowVisible(leftArrow, roomMaster.getCurrentRoom().getLeft() != null);

        setArrowVisible(rightArrow, roomMaster.getCurrentRoom().getRight() != null);

        setArrowVisible(upArrow, roomMaster.getCurrentRoom().getUp() != null);

        setArrowVisible(downArrow, roomMaster.getCurrentRoom().getDown() != null);
    }

    public void moveLeft(View v) {
        if (roomMaster == null) return;
        setArrowVisible(leftArrow,false);
        Log.d("previousRoom", roomMaster.getCurrentRoom().toString());
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getLeft());
        Log.d("currentRoom", roomMaster.getCurrentRoom().toString());
        setArrows();
        MiniMap.playerX --;
    }

    public void moveRight(View v) {
        if (roomMaster == null) return;
        setArrowVisible(rightArrow,false);
        Log.d("previousRoom", roomMaster.getCurrentRoom().toString());
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getRight());
        Log.d("currentRoom", roomMaster.getCurrentRoom().toString());
        setArrows();
        MiniMap.playerX ++;
    }

    public void moveUp(View v) {
        if (roomMaster == null) return;
        setArrowVisible(upArrow,false);
        Log.d("previousRoom", roomMaster.getCurrentRoom().toString());
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getUp());
        Log.d("currentRoom", roomMaster.getCurrentRoom().toString());
        setArrows();
        MiniMap.playerY --;
    }

    public void moveDown(View v) {
        if (roomMaster == null) return;
        setArrowVisible(downArrow,false);
        Log.d("previousRoom", roomMaster.getCurrentRoom().toString());
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getDown());
        Log.d("currentRoom", roomMaster.getCurrentRoom().toString());
        setArrows();
        MiniMap.playerY ++;
    }

    /**
     * Used to give Arrows access to a passed roomMaster instance
     * @param roomMaster the roomMaster instance Arrows will utilize
     */
    public void setRoomMaster(RoomMaster roomMaster) {
        this.roomMaster = roomMaster;
    }

    public void hideArrows() {
        setArrowVisible(leftArrow, false);
        setArrowVisible(rightArrow, false);
        setArrowVisible(upArrow, false);
        setArrowVisible(downArrow, false);
    }

    public void showArrows() {
        setArrowVisible(leftArrow, true);
        setArrowVisible(rightArrow, true);
        setArrowVisible(upArrow, true);
        setArrowVisible(downArrow, true);
    }

    public void disable(boolean state) {
        if (roomMaster == null) return;
        if (isDisabled == state) return;
        isDisabled = state;
        Log.d("isDisabled", String.valueOf(isDisabled));
        if (state) {
            rootView.setVisibility(GONE);
        } else {
            rootView.setVisibility(VISIBLE);
        }
    }
}
