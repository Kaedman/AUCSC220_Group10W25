package com.example.untitleddungeongame.combat;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.untitleddungeongame.ui.OutputText;
import com.example.untitleddungeongame.characters.NPC;
import com.example.untitleddungeongame.characters.Player;
import com.example.untitleddungeongame.hotbar.attacks.QuickAttack;
import com.example.untitleddungeongame.hotbar.items.heals.Apple;
import com.example.untitleddungeongame.hotbar.items.heals.Potion;

public class Combat {
     protected boolean inCombat = false;
     protected final Controller controller;
     protected final Presenter presentor;
     Player player = new Player(50);
     NPC enemy = new NPC("Enemy", 50);
     boolean showItems = false;
     boolean updateItems = false;
     private final AppCompatActivity activity;

     public Combat(AppCompatActivity activity) {
         this.activity = activity;
         presentor = new Presenter(activity);
         controller = new Controller(player, enemy);
         presentor.attackButton.setOnClickListener(this::attackButtonPressed);
         presentor.itemsButton.setOnClickListener(this::itemsButtonPressed);
         presentor.itemBar.setOnClick(this::useItem);
         presentor.attackBar.setOnClick(this::useAttack);
         presentor.setItemBar(player.getEquipped());
         presentor.setAttackBar(player.getAttacks());
         inCombat = true;
         Apple apple = new Apple();
         Potion potion = new Potion();

         QuickAttack quickAttack = new QuickAttack();
         player.addAttack(quickAttack);
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

     public void run() {
         controller.run();
         activity.runOnUiThread(() -> {
                presentor.updateHealth(player, enemy);
                presentor.updateHotBars();
         });
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

}
