package com.example.untitleddungeongame.handlers;

import android.view.View;
import android.widget.Button;

import com.example.untitleddungeongame.characters.Player;
import com.example.untitleddungeongame.misc.ElapseTime;

public class Combat {
     protected boolean inCombat = false;
     private CurrentTurn currentTurn = CurrentTurn.PLAYER_TURN;

     Player player = new Player(50);
     Player enemy = new Player(50);

     private ElapseTime elapseTime = new ElapseTime();
     boolean lastTimeSet = false;

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
            player.setHealthWhenHit(damage);
            currentTurn = CurrentTurn.PLAYER_TURN;
            lastTimeSet = false;
         }

     }

     public void attackButtonPressed(View button) {
        if (!inCombat && currentTurn != CurrentTurn.PLAYER_TURN) return;

        int damage = player.getAttack();
        enemy.setHealthWhenHit(damage);
        currentTurn = CurrentTurn.ENEMY_TURN;
     }

    public void itemsButtonPressed(View button) {

    }

    public boolean isInCombat() {
        return inCombat;
    }

}
