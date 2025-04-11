package com.example.untitleddungeongame.handlers.combat;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.untitleddungeongame.misc.OutputText;
import com.example.untitleddungeongame.R;
import com.example.untitleddungeongame.hotbar.attacks.Attack;
import com.example.untitleddungeongame.hotbar.items.Item;
import com.example.untitleddungeongame.ui.hotbar.HotBar;
import com.example.untitleddungeongame.ui.hotbar.ItemAttackButton;
import com.example.untitleddungeongame.ui.hotbar.ItemAttackButton;

@SuppressLint("SetTextI18n")
public class Presenter {
    protected final AppCompatActivity activity;
    protected final ItemAttackButton itemAttackButton;
    protected final ImageView attackButton, itemsButton;
    private final TextView playerHealth, enemyHealth;

    protected final HotBar<Item> itemBar;
    protected final HotBar<Attack> attackBar;

    boolean showItems = false;
    boolean showAttacks = false;
    boolean newDialog = false;

    public Presenter(AppCompatActivity activity) {
        this.activity = activity;

        itemAttackButton = activity.findViewById(R.id.itemAttackButton);
        attackButton = activity.findViewById(R.id.attack_button);
        itemsButton = activity.findViewById(R.id.items_button);
        playerHealth = activity.findViewById(R.id.player_health);
        enemyHealth = activity.findViewById(R.id.enemy_health);

        itemBar = activity.findViewById(R.id.item_bar);
        attackBar = activity.findViewById(R.id.attack_bar);

        attackBar.disable(true);
        itemBar.disable(true);
    }


    public void optionPressed() {
       if (showItems) {
           switchItems();
       }
       if (showAttacks) {
           switchAttacks();
       }
    }
    public void switchItems() {
        if (OutputText.isInDialog()) return;
        showItems = !showItems;
        itemBar.disable(!showItems);
        itemBar.updateUi();
        if (showAttacks) {
            attackBar.disable(true);
            showAttacks = false;
        }
    }
    public void switchAttacks() {
        if (OutputText.isInDialog()) return;
        showAttacks = !showAttacks;
        attackBar.disable(!showAttacks);
        attackBar.updateUi();
        if (showItems) {
            itemBar.disable(true);
            showItems = false;
        }
    }



    public void setItemBar(Item[] items) {
        itemBar.set(items);
    }
    public void setAttackBar(Attack[] attacks) {
        attackBar.set(attacks);
    }

    public void updateHotBars() {
        if (showItems) {
            itemBar.updateUi();
        }
        if (showAttacks) {
            attackBar.updateUi();
        }
    }
}
