package com.example.untitleddungeongame.handlers.combat;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.untitleddungeongame.floors.Room;
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
    private Enemy enemy;
    private final AppCompatActivity activity;
    private boolean isDisabled = false;

     public Combat(AppCompatActivity activity, Player player) {
         this.activity = activity;
         this.player = player;
         presentor = new Presenter(activity);
         controller = new Controller(player);

         presentor.itemAttackButton.setAttackButtonClickListener(this::attackButtonPressed);
         presentor.itemAttackButton.setItemButtonClickListener(this::itemsButtonPressed);
         presentor.itemBar.setOnClick(this::useItem);
         presentor.attackBar.setOnClick(this::useAttack);
         presentor.setItemBar(player.getEquipped());
         presentor.setAttackBar(player.getAttacks());
     }

     public void run(Room room, boolean isPaused) {
         inCombat = room.getEnemy() != null && !room.getRoomCleared();
         if (!inCombat || isPaused) {
             disableButtons();
             return;
         }
         controller.run(room);
         activity.runOnUiThread(() -> {
            presentor.updateHotBars();
            presentor.itemAttackButton.disable(false);
         });
         isDisabled = false;
     }

     private void attackButtonPressed(View button) {
        if (!inCombat) return;
        presentor.switchAttacks();
     }

    private void itemsButtonPressed(View button) {
        if (!inCombat) return;
        presentor.switchItems();
    }
    private void disableButtons() {
        if (isDisabled) return;
        isDisabled = true;
        activity.runOnUiThread(() -> {
            presentor.itemAttackButton.disable(true);
            presentor.attackBar.disable(true);
            presentor.itemBar.disable(true);
        });
        controller.reset();
    }

    public boolean isInCombat() {
        return inCombat;
    }

    private void useItem(int position) {
        if (!inCombat || OutputText.isInDialog()) return;
        presentor.optionPressed();
        controller.useItem(position, player);
    }

    private void useAttack(int position) {
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

    public void setEventListener(Controller.CombatEventListener listener) {
         controller.eventListener = listener;
    }

}
