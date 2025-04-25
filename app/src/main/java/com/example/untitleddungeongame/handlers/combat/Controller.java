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
    private final ParryController parryController = new ParryController();
    private final ElapseTime elapseTime = new ElapseTime();
    private final CombatEvent combatEvent = new CombatEvent();
    private CombatOption combatOption;

    private final Player player;
    private Enemy enemy;
    private Room currentRoom;

    boolean parried = false;
    boolean isReadyToParry = false;
    boolean shouldCancelParry = false;
    boolean canParry = true;

    public enum CombatEventEnum {
        PLAYER_ATTACK,
        ENEMY_ATTACK,
        PLAYER_ITEM_USE,
        ENEMY_ITEM_USE,
        PLAYER_STATUS_EFFECT,
        ENEMY_STATUS_EFFECT,
        PLAYER_TURN,
        ENEMY_TURN,
        PLAYER_DEATH,
        ENEMY_DEATH,
        PLAYER_PARRY,
        PARRY_WINDOW_OPEN,
        PARRY_WINDOW_CLOSE

    }
    public interface CombatEventListener {
        void run(CombatEventEnum event);
    }
    protected CombatEventListener eventListener = null;

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
        if (eventListener == null) throw new NullPointerException("Event Listener is null");
        if (room.getEnemy() == null) return;
        enemy = room.getEnemy();
        currentRoom = room;
        if (OutputText.isInDialog()) return;
        combatState.runState();
    }

    private void runWaitingState() {
        if (!elapseTime.hasTimeElapsed(750)) return;
        if (player.getSpeed() < enemy.getSpeed()) {
            combatState.combatTurn.initializeEnemyTurn();
        } else {
            combatState.combatTurn.initializePlayerTurn();
        }

        combatState.switchState(CombatState.State.TURN_START);
    }

    private void handleTurn() {
        if (combatOption == null) return;
        if (!elapseTime.hasTimeElapsed(250)) return;
        System.out.println("Combat Turn: " + combatState.currentTurn());
        if (combatState.currentTurn() == CombatTurn.State.PLAYER) {
            eventListener.run(CombatEventEnum.PLAYER_TURN);
            OutputText.setOutputText("Player Turn");
            combatEvent.set(player, enemy, combatOption.getType(), combatOption.getPosition());
        } else if (combatState.currentTurn() == CombatTurn.State.ENEMY) {
            eventListener.run(CombatEventEnum.ENEMY_TURN);
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
        if (combatState.currentTurn() == CombatTurn.State.ENEMY && canParry) {
            if (!parried && !shouldCancelParry) {
                if (!isReadyToParry) {
                    if (!elapseTime.hasTimeElapsed(400)) return; // This is the time before the parry window opens
                    isReadyToParry = true;
                    eventListener.run(CombatEventEnum.PARRY_WINDOW_OPEN);
                    return;
                }
                if (parryController.isTryingToParry() && isReadyToParry) {
                    System.out.println("Parry Attempted");
                    parried = true;
                }
                if (!elapseTime.hasTimeElapsed(500)) return; // This effects the parry window time
                eventListener.run(CombatEventEnum.PARRY_WINDOW_CLOSE);
                canParry = false;
            }
            if (parried && !shouldCancelParry && parryController.isTryingToParry()) {
                parried = false;
                shouldCancelParry = true;
            }
        }
        if (!elapseTime.hasTimeElapsed(250)) return;
        Entity source = combatEvent.getSource();
        Entity target = combatEvent.getTarget();
        Attack attack = source.getAttacks()[combatEvent.getPosition()];

        StringBuilder outputText = new StringBuilder(source.getName() + " used " + attack.getName() + " on " + target.getName());

        if (combatState.currentTurn() == CombatTurn.State.ENEMY && parried) {
            parried = false;
            combatState.switchState(CombatState.State.PARRY);
            eventListener.run(CombatEventEnum.ENEMY_ATTACK);
        } else {
            if (combatState.currentTurn() == CombatTurn.State.ENEMY) {
                eventListener.run(CombatEventEnum.ENEMY_ATTACK);
            } else if (combatState.currentTurn() == CombatTurn.State.PLAYER) {
                eventListener.run(CombatEventEnum.PLAYER_ATTACK);
            }
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
        } else {
            if (combatState.currentTurn() == CombatTurn.State.ENEMY) {
                eventListener.run(CombatEventEnum.ENEMY_ITEM_USE);
            } else if (combatState.currentTurn() == CombatTurn.State.PLAYER) {
                eventListener.run(CombatEventEnum.PLAYER_ITEM_USE);
            }
        }

        OutputText.setOutputText(outputText);
        combatState.switchState(CombatState.State.TURN_START);
    }
    private void statusEffect() {

    }

    private void handleParry() {
        if (!elapseTime.hasTimeElapsed(100)) return;
        eventListener.run(CombatEventEnum.PLAYER_PARRY);
        StringBuilder outputText = new StringBuilder("But Player Parried");
        Entity source = combatEvent.getSource();
        Entity target = combatEvent.getTarget();
        Attack attack = source.getAttacks()[combatEvent.getPosition()];
        String result = target.takeDamage(attack, -target.getDefense() * 2);
        outputText.append("\n").append(result);
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
        combatState.combatTurn.reset();
        combatOption = null;
        canParry = true;
        shouldCancelParry = false;
        if (enemy.isDead()) {
            if (!elapseTime.hasTimeElapsed(100)) return;
            eventListener.run(CombatEventEnum.ENEMY_DEATH);
            OutputText.setOutputText("Enemy Defeated");
            currentRoom.setRoomCleared(true);
        } else if (player.isDead()) {
            if (!elapseTime.hasTimeElapsed(100)) return;
            eventListener.run(CombatEventEnum.PLAYER_DEATH);
            OutputText.setOutputText("Player Defeated");
        }
        combatState.switchState(CombatState.State.READY);
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
        if (combatState.getCurrentState() != CombatState.State.ATTACK || combatState.currentTurn() != CombatTurn.State.ENEMY) return;
        if (isReadyToParry && canParry) {
            parryController.screenTapped = true;
        }
    }
    public void reset() {
        combatState.reset();
        parryController.reset();
        elapseTime.reset();
        combatOption = null;
        canParry = true;
    }
}
