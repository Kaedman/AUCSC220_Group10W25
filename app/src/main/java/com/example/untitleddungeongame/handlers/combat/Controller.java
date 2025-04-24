package com.example.untitleddungeongame.handlers.combat;

import com.example.untitleddungeongame.entity.Entity;
import com.example.untitleddungeongame.floors.Room;
import com.example.untitleddungeongame.misc.OutputText;
import com.example.untitleddungeongame.entity.Enemy;
import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.hotbar.attacks.Attack;
import com.example.untitleddungeongame.hotbar.items.Item;
import com.example.untitleddungeongame.misc.ElapseTime;

public class Controller {

    protected final CombatState combatState = new CombatState();
    private CombatOption combatOption;
    private ParryController parryController = new ParryController();
    private CombatEvent combatEvent = new CombatEvent();
    private final ElapseTime elapseTime = new ElapseTime();

    private final Player player;
    private Enemy enemy;
    private Room currentRoom;

    boolean parried = false;
    boolean isReadyToParry = false;
    boolean canParry = true;

    public Controller(Player player) {
        this.player = player;

        combatState.setStateCallback(CombatState.State.WAITING, this::runWaitingState);
        combatState.setStateCallback(CombatState.State.TURN_START, this::handleTurn);
        combatState.setStateCallback(CombatState.State.ATTACK, this::attacking);
        combatState.setStateCallback(CombatState.State.ITEM_USE, this::itemUsage);
        combatState.setStateCallback(CombatState.State.STATUS_EFFECT, this::statusEffect);
        combatState.setStateCallback(CombatState.State.PARRY, this::handleParry);
        combatState.setStateCallback(CombatState.State.ENDING, this::ending);
    }

    public void run(Room room) {
        if (room.getEnemy() == null) return;
        enemy = room.getEnemy();
        currentRoom = room;
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
        if (!elapseTime.hasTimeElapsed(250)) return;
        System.out.println("Combat Turn: " + combatState.currentTurn());
        if (combatState.currentTurn() == CombatTurn.State.PLAYER) {
            OutputText.setOutputText("Player Turn");
            combatEvent.set(player, enemy, combatOption.getType(), combatOption.getPosition());
        } else if (combatState.currentTurn() == CombatTurn.State.ENEMY) {
            combatOption = new CombatOption<>(enemy.getAttacks()[0], 0);
            OutputText.setOutputText("Enemy Turn");
            combatEvent.set(enemy, player, combatOption.getType(), combatOption.getPosition());
        } else {
            OutputText.setOutputText("Combat Sequence Ended");
            combatState.switchState(CombatState.State.READY);
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
                if (!elapseTime.hasTimeElapsed(250)) return;
                if (!isReadyToParry) {
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
            if (!elapseTime.hasTimeElapsed(250)) return;
        }
        Entity source = combatEvent.getSource();
        Entity target = combatEvent.getTarget();
        Attack attack = source.getAttacks()[combatEvent.getPosition()];

        StringBuilder outputText = new StringBuilder(source.getName() + " used " + attack.getName() + " on " + target.getName());


        if (combatState.currentTurn() == CombatTurn.State.ENEMY && parried) {
            parried = false;
            combatState.switchState(CombatState.State.PARRY);
        } else {
            String result = target.takeDamage(attack, source.getAttack());
            outputText.append("\n").append(result);
            combatState.switchState(CombatState.State.TURN_START);
        }
        if (target.isDead() || source.isDead()) {
            combatState.switchState(CombatState.State.ENDING);
        }
        OutputText.setOutputText(outputText.toString());
    }
    private void itemUsage() {
        if (!elapseTime.hasTimeElapsed(100)) return;
        Item item = combatEvent.getSource().getEquipped()[combatEvent.getPosition()];
        boolean result = combatEvent.getSource().useItem(combatEvent.getPosition());
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
        if (!elapseTime.hasTimeElapsed(100)) return;
        StringBuilder outputText = new StringBuilder("But Player Parried");
        Entity source = combatEvent.getSource();
        Entity target = combatEvent.getTarget();
        Attack attack = source.getAttacks()[combatEvent.getPosition()];
        String result = target.takeDamage(attack, -target.getDefense());
        outputText.append("and").append(result);
        OutputText.setOutputText(outputText.toString());

        if (target.isDead() || source.isDead()) {
            combatState.switchState(CombatState.State.ENDING);
        } else {
            combatState.switchState(CombatState.State.TURN_START);
        }
        isReadyToParry = false;
        canParry = true;
    }

    public void ending() {
        if (!elapseTime.hasTimeElapsed(100)) return;
        if (enemy.isDead()) {
            OutputText.setOutputText("Enemy Defeated");
            currentRoom.setRoomCleared(true);
            combatState.switchState(CombatState.State.READY);
            return;
        } else if (player.isDead()) {
            OutputText.setOutputText("Player Defeated");
            combatState.switchState(CombatState.State.READY);
            return;
        }
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

    public void screenTapped() {
        if (combatState.getCurrentState() != CombatState.State.ATTACK && combatState.currentTurn() != CombatTurn.State.ENEMY) return;
        parryController.screenTapped = true;
    }
    public void reset() {
        combatState.reset();
        parryController.reset();
        elapseTime.reset();
        combatOption = null;
        canParry = true;
    }
}
