package com.example.untitleddungeongame.combat;

import com.example.untitleddungeongame.misc.ElapseTime;

public class Controller {

    private final CombatState combatState = new CombatState();
    private final CombatTurn combatTurn = new CombatTurn();
    private final ElapseTime elapseTime = new ElapseTime();

    private Runnable playerEvent;
    public interface EnemyRunnable {
        boolean run();
    }
    private EnemyRunnable enemyEvent;

    private ParryController parryController = new ParryController();

    String outputText = "";
    boolean newText = false;
    boolean inDialog = false;

    boolean parried = false;

    public Controller() {
    }

    public void run(boolean inDialog) {
        if (inDialog) return;
        if (combatState.isReady()) return;
        if (combatState.isWaiting()) runWaitingState();
        if (combatState.isAttacking()) runAttackingState();
    }

    private void runAttackingState() {
        if (combatTurn.isEnemyTurn() && parryController.isTryingToParry()) {
            parryController.setParrying(true);
        }
        if (!elapseTime.hasTimeElapsedSeconds(1)) return;

        if (combatTurn.isPlayerTurn()) {
            playerTurn();
        } else if (combatTurn.isEnemyTurn()) {
            parried = enemyTurn();
        } else {
            setNewText("Combat Sequence Ended");
            parryController.setParrying(false);
            combatState.switchState(false);
            return;
        }
        combatTurn.switchTurn();
    }

    private void runWaitingState() {
        if (!elapseTime.hasTimeElapsed(750)) return;
        setNewText("Combat Started, Player is faster, Player Turn");
//        showItems = false;
        combatTurn.initializePlayerTurn();
        combatState.switchState(false);
    }

    private void playerTurn() {
        setNewText("Player Attacked");
        playerEvent.run();
    }

    private boolean enemyTurn() {
        setNewText("Enemy Attacked");
        return enemyEvent.run();
    }


    public void setPlayerEvent(Runnable playerEvent) {
        this.playerEvent = playerEvent;
    }

    public void setEnemyEvent(EnemyRunnable enemyEvent) {
        this.enemyEvent = enemyEvent;
    }

    public void setReady() {
        if (!combatState.isReady()) return;
        setNewText("Ready to start combat");
        combatState.switchState(false);
    }

    public void setInDialog(boolean state) {
        inDialog = state;
    }

    public void setPlayerParrying(boolean state) {
        if (!combatTurn.isEnemyTurn()) return;
        System.out.println("Screen tapped");
        parryController.screenTapped = state;
    }

    public void setNewText(String newText) {
        this.newText = true;
        this.outputText = newText;
    }

    public boolean isNewText() {
        if (!newText) return false;
        newText = false;
        return true;
    }

    public void screenTapped(boolean dialogFinished) {
        setPlayerParrying(true);
    }

    public boolean isPlayerParrying() {
        return parryController.hasParryBeenSet();
    }




}
