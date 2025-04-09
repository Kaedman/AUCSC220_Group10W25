package com.example.untitleddungeongame.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.untitleddungeongame.Floors.Boss;
import com.example.untitleddungeongame.Floors.Rest;
import com.example.untitleddungeongame.Floors.RoomMaster;
import com.example.untitleddungeongame.R;

public class ConfirmCancelMenu extends LinearLayout {
    private RoomMaster roomMaster;
    private LinearLayout rootView;
    private Button confirmButton;
    private Button cancelButton;

    public ConfirmCancelMenu(Context context, AttributeSet attrs, RoomMaster roomMaster) {
        super(context, attrs);
        init(context, roomMaster);
    }

    public ConfirmCancelMenu(Context context, RoomMaster roomMaster) {
        super(context);
        init(context, roomMaster);
    }

    private void init(Context context, RoomMaster roomMaster) {
        this.roomMaster = roomMaster;
        LayoutInflater.from(context).inflate(R.layout.confirm_cancel_menu, this, true);
        rootView = findViewById(R.id.confirm_cancel_menu);
        confirmButton = findViewById(R.id.confirm);
        cancelButton = findViewById(R.id.cancel);
    }

    public void onConfirm(View v) {
        if (roomMaster.getCurrentRoom() instanceof Rest) {
            //roomMaster.useRest();
            enterRest(); // Should be updated if there are any other triggers added in enterRest,
            // right now just used to update the dialog
        } else if (roomMaster.getCurrentRoom() instanceof Boss) {
            // TODO: GO TO NEXT ROOM, not necessary for prototype
            //((Boss) roomMaster.getCurrentRoom().nextFloor());
        }
    }

    public void onCancel(View v) {
        // TODO: HIDE DIALOG
        confirmButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
    }

    public void enterRest() {
        if (((Rest) roomMaster.getCurrentRoom()).usedRest()) {
            // TODO: ADD HEAL? DIALOG
            confirmButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
        } else {
            // TODO: ADD HEAL USED DIALOG
        }
    }

    public void showFloorDialog() {
        // TODO: ADD MOVE TO NEXT FLOOR? DIALOG not necessary in prototype
        confirmButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
    }
}
