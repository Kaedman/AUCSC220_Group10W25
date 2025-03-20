package com.example.untitleddungeongame;

import android.view.View;
import android.widget.Button;

public class CombatMechanic {
     protected boolean isInCombat = false;
     private CurrentTurn currentTurn = CurrentTurn.PLAYER_TURN;

     Player player = new Player(50);
     Player enemy = new Player(50);

     long lastTime = System.currentTimeMillis();
     long currentTime = System.currentTimeMillis();
     boolean lastTimeSet = false;

     private
     enum CurrentTurn {
          PLAYER_TURN,
          ENEMY_TURN
     }

     public CombatMechanic(Button attackButton, Button itemsButton) {
          attackButton.setOnClickListener(this::attackButtonPressed);
          itemsButton.setOnClickListener(this::itemsButtonPressed);
          currentTurn = CurrentTurn.PLAYER_TURN;
          isInCombat = true;
     }

     public void setCombat(boolean state) {
         isInCombat = state;
     }

     public void runCombat() {
         currentTime = System.currentTimeMillis();
         if (currentTurn == CurrentTurn.ENEMY_TURN) enemyTurn();
     }

     private void playerTurn() {

     }

     private void enemyTurn() {
         if (!lastTimeSet) { // This is adding a delay to the enemy's attack
            lastTime = currentTime;
            lastTimeSet = true;
         }

         if (currentTime - lastTime > 1000) {
            int damage = enemy.attack;
            player.setHealthWhenHit(damage);
            currentTurn = CurrentTurn.PLAYER_TURN;
            lastTimeSet = false;
         }

     }

     public void attackButtonPressed(View button) {
        if (!isInCombat && currentTurn != CurrentTurn.PLAYER_TURN) return;

        int damage = player.attack;
        enemy.setHealthWhenHit(damage);
        currentTurn = CurrentTurn.ENEMY_TURN;
     }

    public void itemsButtonPressed(View button) {

    }

}
