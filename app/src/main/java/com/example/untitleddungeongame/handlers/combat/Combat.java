package com.example.untitleddungeongame.handlers.combat;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.untitleddungeongame.misc.OutputText;
import com.example.untitleddungeongame.entity.Enemy;
import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.hotbar.attacks.QuickAttack;
import com.example.untitleddungeongame.hotbar.items.heals.Apple;
import com.example.untitleddungeongame.hotbar.items.heals.Potion;

public class Combat {
    protected boolean inCombat = false;
    protected final Controller controller;
    protected final Presenter presentor;
    private final Player player;
    Enemy enemy;
    private final AppCompatActivity activity;
    private boolean isDisabled = false;

     public Combat(AppCompatActivity activity, Player player, Enemy enemy) {
         this.activity = activity;
         this.player = player;
         this.enemy = enemy;
         presentor = new Presenter(activity);
         controller = new Controller(player, enemy);

         presentor.itemAttackButton.setAttackButtonClickListener(this::attackButtonPressed);
         presentor.itemAttackButton.setItemButtonClickListener(this::itemsButtonPressed);
         presentor.itemBar.setOnClick(this::useItem);
         presentor.attackBar.setOnClick(this::useAttack);
         presentor.setItemBar(player.getEquipped());
         presentor.setAttackBar(player.getAttacks());
     }

     public void setCombat(boolean state) {
         inCombat = state;
     }

     public void run() {
         if (!inCombat) {
             if (!isDisabled) {
                 activity.runOnUiThread(() -> {
                     presentor.itemAttackButton.disable(true);
                     presentor.attackBar.disable(true);
                     presentor.itemBar.disable(true);
                 });
                    isDisabled = true;
             } else {
                 isDisabled = false;
             }
             controller.reset();
             return;
         }
         controller.run();
         activity.runOnUiThread(() -> {
            presentor.updateHotBars();
            presentor.itemAttackButton.disable(false);
         });
         isDisabled = false;
     }

     public void attackButtonPressed(View button) {
        if (!inCombat) return;
        presentor.switchAttacks();
     }

    public void itemsButtonPressed(View button) {
        if (!inCombat) return;
        presentor.switchItems();
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public void useItem(int position) {
        if (!inCombat || OutputText.isInDialog()) return;
        presentor.optionPressed();
        controller.useItem(position, player);
    }

    public void useAttack(int position) {
        if (!inCombat || OutputText.isInDialog()) return;
        presentor.optionPressed();
        controller.useAttack(position, player);
    }

    public void screenTappedOn(float touchX, float touchY) {
        if (!inCombat || OutputText.isInDialog()) return;
        controller.screenTapped();
    }

    public Player getPlayer() {
        return player;
    }
}
