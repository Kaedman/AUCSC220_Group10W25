package com.example.untitleddungeongame.combat;

import com.example.untitleddungeongame.characters.Entity;
import com.example.untitleddungeongame.ui.OutputText;
import com.example.untitleddungeongame.characters.NPC;
import com.example.untitleddungeongame.characters.Player;
import com.example.untitleddungeongame.hotbar.attacks.Attack;
import com.example.untitleddungeongame.hotbar.items.Item;
import com.example.untitleddungeongame.misc.ElapseTime;

public class Controller {

    private final CombatState combatState = new CombatState();
    private CombatOption combatOption;
    private ParryController parryController = new ParryController();
    private CombatEvent combatEvent = new CombatEvent();
    private final ElapseTime elapseTime = new ElapseTime();

    private final Player player;
    private final NPC enemy;

    boolean parried = false;
    boolean isReadyToParry = false;
    boolean canParry = true;

    public Controller(Player player, NPC enemy) {
        this.player = player;
        this.enemy = enemy;

        combatState.setStateCallback(CombatState.State.WAITING, this::runWaitingState);
        combatState.setStateCallback(CombatState.State.TURN_START, this::handleTurn);
        combatState.setStateCallback(CombatState.State.ATTACK, this::attacking);
        combatState.setStateCallback(CombatState.State.ITEM_USE, this::itemUsage);
        combatState.setStateCallback(CombatState.State.STATUS_EFFECT, this::statusEffect);
        combatState.setStateCallback(CombatState.State.PARRY, this::handleParry);
        combatState.setStateCallback(CombatState.State.ENDING, this::ending);


    }

    public void run() {
        if (OutputText.isInDialog()) return;
        combatState.runState();
    }

    private void runWaitingState() {
        if (!elapseTime.hasTimeElapsed(750)) return;
        OutputText.setOutputText("Combat Started, Player is faster, Player Turn");
        combatState.combatTurn.initializePlayerTurn();
        combatState.switchState(CombatState.State.TURN_START);
    }

    private void handleTurn() {
        if (combatOption == null) return;
        if (!elapseTime.hasTimeElapsedSeconds(1)) return;
        if (combatState.currentTurn() == CombatTurn.State.PLAYER) {
            OutputText.setOutputText("Player Turn");
            combatEvent.set(player, enemy, combatOption.getType(), combatOption.getPosition());
        } else if (combatState.currentTurn() == CombatTurn.State.ENEMY) {
            combatOption = new CombatOption<>(enemy.getAttacks()[0], 0);
            OutputText.setOutputText("Enemy Turn");
            combatEvent.set(enemy, player, combatOption.getType(), combatOption.getPosition());
        } else {
            OutputText.setOutputText("Combat Sequence Ended");
            combatState.switchState(CombatState.State.ENDING);
            return;
        }
        if (combatOption.getType() == CombatOption.CombatOptionType.ATTACK) {
            combatState.switchState(CombatState.State.ATTACK);
        } else if (combatOption.getType() == CombatOption.CombatOptionType.ITEM) {
            combatState.switchState(CombatState.State.ITEM_USE);
        }

    }
    private void attacking() {
        if (combatState.currentTurn() == CombatTurn.State.ENEMY) {
            if (canParry && !parried) {
                if (!isReadyToParry && !elapseTime.hasTimeElapsed(250)) {
                    isReadyToParry = true;
                    return;
                }
                System.out.println("Parry is ready");
                if (parryController.isTryingToParry() && isReadyToParry && !parried) {
                    System.out.println("Parry is set");
                    parried = true;
                }
                if (!elapseTime.hasTimeElapsed(500)) return;
                canParry = false;
            }
            if (!elapseTime.hasTimeElapsed(500)) return;
        } else {
            if (!elapseTime.hasTimeElapsed(750)) return;
        }

        Attack attack = combatEvent.getSource().getAttacks()[combatEvent.getPosition()];
        String result = combatEvent.getTarget().takeDamage(attack);
        String outputText = combatEvent.getSource().getName() + " used " + attack.getName() + " on " + combatEvent.getTarget().getName() + "\n" +
                result;
        OutputText.setOutputText(outputText);
        if (combatState.currentTurn() == CombatTurn.State.ENEMY) {
            if (parried) {
                parried = false;
                combatState.switchState(CombatState.State.PARRY);
            } else {
                combatState.switchState(CombatState.State.TURN_START);
            }
        } else {
            combatState.switchState(CombatState.State.TURN_START);
        }
    }
    private void itemUsage() {
        if (!elapseTime.hasTimeElapsedSeconds(1)) return;
        Item item = combatEvent.getSource().getEquipped()[combatEvent.getPosition()];
        boolean result = combatEvent.getTarget().useItem(combatEvent.getPosition());
        String outputText = combatEvent.getSource().getName() + " used " + item.getName();
        if (!result) {
            outputText += "\nBut it failed";
        }

        OutputText.setOutputText(outputText);

        combatState.switchState(CombatState.State.TURN_START);
    }
    private void statusEffect() {

    }

    private void handleParry() {
        if (!elapseTime.hasTimeElapsed(500)) return;
        OutputText.setOutputText("But Player Parried");
        combatState.switchState(CombatState.State.TURN_START);
        isReadyToParry = false;
        canParry = true;
    }

    public void ending() {
        if (!elapseTime.hasTimeElapsedSeconds(1)) return;
        OutputText.setOutputText("Combat Ended");
        combatState.switchState(CombatState.State.READY);
        combatState.combatTurn.resetTurns();
        combatOption = null;
        canParry = true;
    }

    private void enemyTurn() {
        if (!elapseTime.hasTimeElapsedSeconds(1)) return;
        OutputText.setOutputText("Enemy Attacked");
        return;
    }

    public void useItem(int pos, Player player) {
        if (!combatState.isReady()) return;
        combatOption = new CombatOption<>(player.getEquipped()[pos], pos);
        setReady();
    }

    public void useAttack(int pos, Player player) {
        if (!combatState.isReady()) return;
        combatOption = new CombatOption<>(player.getAttacks()[pos], pos);
        setReady();
    }

    private void setReady() {
        if (!combatState.isReady()) return;
        OutputText.setOutputText("Ready to start combat");
        combatState.switchState(CombatState.State.WAITING);
    }


    public void setPlayerParrying(boolean state) {
        if (combatState.currentTurn() != CombatTurn.State.ENEMY) return;
        parryController.screenTapped = state;
    }


    public void screenTapped() {
        setPlayerParrying(true);
    }

    public boolean isPlayerParrying() {
        return parryController.hasParryBeenSet();
    }




}
