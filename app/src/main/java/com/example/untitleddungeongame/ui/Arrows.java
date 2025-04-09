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
        leftArrow.setOnClickListener(this :: moveLeft);
        rightArrow = findViewById(R.id.right_arrow);
        rightArrow.setOnClickListener(this :: moveRight);
        upArrow = findViewById(R.id.up_arrow);
        upArrow.setOnClickListener(this :: moveUp);
        downArrow = findViewById(R.id.down_arrow);
        downArrow.setOnClickListener(this :: moveDown);
    }

    public void setArrowVisible(ImageView v, boolean activated) {
        if (activated) {
            v.setVisibility(VISIBLE);

            /*switch (v.getResources().getResourceName(v.getId())) {
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
            }*/
        } else {
            v.setVisibility(GONE);

            /*switch (v.getResources().getResourceName(v.getId())) {
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
            }*/
        }
    }

    /**
     * Shows or hides the arrows depending on whether or not the currentRoom has associated
     * neighbouring rooms
     */
    public void setArrows() {
        Log.d("floorMap", roomMaster.toString());
        Log.d("currentId", roomMaster.getCurrentRoom().toString());
        setArrowVisible(leftArrow, roomMaster.getCurrentRoom().getLeft() != null);

        setArrowVisible(rightArrow, roomMaster.getCurrentRoom().getRight() != null);

        setArrowVisible(upArrow, roomMaster.getCurrentRoom().getUp() != null);

        setArrowVisible(downArrow, roomMaster.getCurrentRoom().getDown() != null);
    }

    public void moveLeft(View v) {
        setArrowVisible(leftArrow,false);
        Log.d("previousRoom", roomMaster.getCurrentRoom().toString());
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getLeft());
        Log.d("currentRoom", roomMaster.getCurrentRoom().toString());
        setArrows();
    }

    public void moveRight(View v) {
        setArrowVisible(rightArrow,false);
        Log.d("previousRoom", roomMaster.getCurrentRoom().toString());
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getRight());
        Log.d("currentRoom", roomMaster.getCurrentRoom().toString());
        setArrows();
    }

    public void moveUp(View v) {
        setArrowVisible(upArrow,false);
        Log.d("previousRoom", roomMaster.getCurrentRoom().toString());
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getUp());
        Log.d("currentRoom", roomMaster.getCurrentRoom().toString());
        setArrows();
    }

    public void moveDown(View v) {
        setArrowVisible(downArrow,false);
        Log.d("previousRoom", roomMaster.getCurrentRoom().toString());
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getDown());
        Log.d("currentRoom", roomMaster.getCurrentRoom().toString());
        setArrows();
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
}
