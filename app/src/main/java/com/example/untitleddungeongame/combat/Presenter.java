package com.example.untitleddungeongame.combat;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.untitleddungeongame.R;
import com.example.untitleddungeongame.characters.Entity;
import com.example.untitleddungeongame.characters.Player;
import com.example.untitleddungeongame.ui.CustomDialog;
import com.example.untitleddungeongame.ui.ItemBar;

@SuppressLint("SetTextI18n")
public class Presenter {
    protected final AppCompatActivity activity;
    protected final Button attackButton, itemsButton;
    private final TextView playerHealth, enemyHealth;
    protected final ItemBar itemBar;
    protected final CustomDialog combatDialog;

    boolean showItems = false;
    boolean updateItems = false;
    boolean newDialog = false;

    public Presenter(AppCompatActivity activity) {
        this.activity = activity;

        attackButton = activity.findViewById(R.id.attack_button);
        itemsButton = activity.findViewById(R.id.items_button);
        itemBar = new ItemBar(activity);
        playerHealth = activity.findViewById(R.id.player_health);
        enemyHealth = activity.findViewById(R.id.enemy_health);
        combatDialog = activity.findViewById(R.id.combat_dialog);

        attackButton.setText("Attack");
        itemsButton.setText("Items");
    }


    public void updateHealth(Entity player, Entity enemy) {
        playerHealth.setText("Player Health: " + player.getHealth());
        enemyHealth.setText("Enemy Health: " + enemy.getHealth());
    }

    public void updateItems(Player player) {
        itemBar.displayButtons(showItems);
        if (showItems || updateItems) {
            itemBar.setItemButtons(player.getEquipped());
            updateItems = false;
        }
    }

    public void switchItems() {
        showItems = !showItems;
        updateItems = true;
    }

    public void setDialogText(String text, boolean autoClose) {
        activity.runOnUiThread(() -> {
            combatDialog.setText(text);
        });
    }

    public void closeDialog() {
        activity.runOnUiThread(combatDialog::closeDialog);
    }
}
