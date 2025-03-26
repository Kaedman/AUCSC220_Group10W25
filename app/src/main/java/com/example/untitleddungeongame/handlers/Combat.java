package com.example.untitleddungeongame.handlers;

import android.view.View;
import android.widget.Button;

import com.example.untitleddungeongame.characters.Player;
import com.example.untitleddungeongame.items.heals.Apple;
import com.example.untitleddungeongame.items.heals.Potion;
import com.example.untitleddungeongame.misc.ElapseTime;

public class Combat {
     protected boolean inCombat = false;
     private CurrentTurn currentTurn = CurrentTurn.PLAYER_TURN;

     Player player = new Player(50);
     Player enemy = new Player(50);

     private ElapseTime elapseTime = new ElapseTime();
     boolean lastTimeSet = false;
     boolean showItems = false;
     boolean updateItems = false;

     private
     enum CurrentTurn {
          PLAYER_TURN,
          ENEMY_TURN
     }

     public Combat(Button attackButton, Button itemsButton) {
          attackButton.setOnClickListener(this::attackButtonPressed);
          itemsButton.setOnClickListener(this::itemsButtonPressed);
          currentTurn = CurrentTurn.PLAYER_TURN;
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

     public void runCombat() {
         if (currentTurn == CurrentTurn.ENEMY_TURN) enemyTurn();
     }

     private void playerTurn() {

     }

     private void enemyTurn() {
         if (elapseTime.hasTimeElapsed(1000)) {
            int damage = enemy.getAttack();
            player.attack(damage);
            currentTurn = CurrentTurn.PLAYER_TURN;
            lastTimeSet = false;
         }

     }

     public void attackButtonPressed(View button) {
        if (!inCombat && currentTurn != CurrentTurn.PLAYER_TURN) return;

        int damage = player.getAttack();
        enemy.attack(damage);
        currentTurn = CurrentTurn.ENEMY_TURN;
     }

    public void itemsButtonPressed(View button) {
        showItems = !showItems;
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public void useItem(int position) {
        if (player.useItem(position)) {
            updateItems = true;
            currentTurn = CurrentTurn.ENEMY_TURN;
        }
    }

}
