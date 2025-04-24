package com.example.untitleddungeongame.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.untitleddungeongame.floors.Boss;
import com.example.untitleddungeongame.floors.Rest;
import com.example.untitleddungeongame.floors.RoomMaster;
import com.example.untitleddungeongame.R;

public class ConfirmCancelMenu extends ConstraintLayout {
    private RoomMaster roomMaster;
    private ConstraintLayout rootView;
    private PixelButton confirmButton;
    private PixelButton cancelButton;

    public ConfirmCancelMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ConfirmCancelMenu(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.confirm_cancel, this);
        rootView = findViewById(R.id.confirm_cancel);
        confirmButton = findViewById(R.id.confirm_button);
        cancelButton = findViewById(R.id.cancel_button);

        confirmButton.setOnClickListener(this::onConfirm);
        cancelButton.setOnClickListener(this::onCancel);
    }

    public void onConfirm(View v) {
        if (roomMaster.getCurrentRoom() instanceof Rest) {
            ((Rest) roomMaster.getCurrentRoom()).useRest(roomMaster.getPlayer());
            roomMaster.getCurrentRoom().setRoomCleared(true);
            roomMaster.showArrows();
        } else if (roomMaster.getCurrentRoom() instanceof Boss) {
            // TODO: GO TO NEXT ROOM, not necessary for prototype
            //((Boss) roomMaster.getCurrentRoom().nextFloor());
        }

        hide();
    }

    public void onCancel(View v) {
        hide();
        if (roomMaster.getCurrentRoom() instanceof Rest) {
            roomMaster.getCurrentRoom().setRoomCleared(true);
            roomMaster.showArrows();
        }
    }

    public void showFloorDialog() {
        // TODO: ADD MOVE TO NEXT FLOOR? DIALOG not necessary in prototype
        confirmButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
    }

    public void show() {
        rootView.setVisibility(VISIBLE);
    }


    public void hide() {
        rootView.setVisibility(GONE);
    }

    public void setRoomMaster(RoomMaster roomMaster) {
        this.roomMaster = roomMaster;
    }
}
