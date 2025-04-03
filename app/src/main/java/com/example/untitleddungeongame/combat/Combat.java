package com.example.untitleddungeongame.combat;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.untitleddungeongame.characters.NPC;
import com.example.untitleddungeongame.characters.Player;
import com.example.untitleddungeongame.items.heals.Apple;
import com.example.untitleddungeongame.items.heals.Potion;

public class Combat {
     protected boolean inCombat = false;
     protected boolean inDialog = false;
     protected final Controller controller = new Controller();
     protected final Presenter presentor;
     Player player = new Player(50);
     NPC enemy = new NPC(50);
     boolean showItems = false;
     boolean updateItems = false;
     private AppCompatActivity activity;

     public Combat(AppCompatActivity activity) {
         this.activity = activity;
         presentor = new Presenter(activity);
         presentor.attackButton.setOnClickListener(this::attackButtonPressed);
         presentor.itemsButton.setOnClickListener(this::itemsButtonPressed);
         presentor.itemBar.setOnClick(this::useItem);
         controller.setPlayerEvent(this::playerTurn);
         controller.setEnemyEvent(this::enemyTurn);
         inCombat = true;
         Apple apple = new Apple();
         Potion potion = new Potion();
         player.addItem(apple);
         player.addItem(apple);
         player.addItem(apple);
         player.addItem(potion);
         player.addItem(apple);
         player.addItem(apple);
         player.addItem(potion);
     }

     public void setCombat(boolean state) {
         inCombat = state;
     }

    private void playerTurn() {
         int damage = player.getAttack();
        enemy.takeDamage(damage);
        if (enemy.getHealth() <= 0) {
            inCombat = false;
        }
    }

    private boolean enemyTurn() {
         if (controller.isPlayerParrying()) {
             presentor.setDialogText("You parried the attack!");
             return true;
         }
         int damage = enemy.getAttack();
        player.takeDamage(damage);
        if (player.getHealth() <= 0) {
            inCombat = false;
        }
        return false;
    }

     public void run() {
         if (!inCombat) {
             activity.runOnUiThread(() -> {
                presentor.itemBar.displayButtons(false);

             });
             return;
         }

         if (controller.isNewText()) {
             presentor.setDialogText(controller.outputText);
             inDialog = true;
         }
         controller.run(inDialog);
         activity.runOnUiThread(() -> {
                presentor.updateHealth(player, enemy);
                presentor.updateItems(player);
                presentor.combatDialog.updateText();
         });
     }

     public void attackButtonPressed(View button) {
        if (!inCombat) return;

        controller.setReady();
     }

    public void itemsButtonPressed(View button) {
        if (!inCombat) return;

        presentor.switchItems();
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public void useItem(int position) {
        System.out.println("Using item at position " + position);
        if (player.useItem(position)) {
            updateItems = true;
            controller.setReady();
        }
    }

    public void screenTappedOn(float touchX, float touchY) {
        if (presentor.combatDialog.isTextFinishedUpdating()) {
            presentor.closeDialog();
            inDialog = false;
        }
        controller.screenTapped(inDialog);
    }

}
