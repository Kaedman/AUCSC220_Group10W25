package com.example.untitleddungeongame.handlers;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.GameTouchListener;
import com.example.untitleddungeongame.MainActivity;
import com.example.untitleddungeongame.R;
import com.example.untitleddungeongame.animations.AnimatedSprite;
import com.example.untitleddungeongame.animations.Animation;
import com.example.untitleddungeongame.animations.Sprite;
import com.example.untitleddungeongame.entity.Enemy;
import com.example.untitleddungeongame.entity.Goblin;
import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.floors.Boss;
import com.example.untitleddungeongame.floors.Encounter;
import com.example.untitleddungeongame.floors.Rest;
import com.example.untitleddungeongame.floors.Room;
import com.example.untitleddungeongame.floors.RoomMaster;
import com.example.untitleddungeongame.handlers.combat.Combat;
import com.example.untitleddungeongame.handlers.combat.Controller;
import com.example.untitleddungeongame.hotbar.attacks.QuickAttack;
import com.example.untitleddungeongame.hotbar.items.heals.Apple;
import com.example.untitleddungeongame.hotbar.items.heals.Potion;
import com.example.untitleddungeongame.misc.ElapseTime;
import com.example.untitleddungeongame.ui.Arrows;
import com.example.untitleddungeongame.ui.CustomDialog;
import com.example.untitleddungeongame.misc.OutputText;
import com.example.untitleddungeongame.ui.DeathScreen;
import com.example.untitleddungeongame.ui.MiniMap;
import com.example.untitleddungeongame.ui.ParticleSystem;
import com.example.untitleddungeongame.ui.RoomVisual;

@SuppressLint("ViewConstructor")
public class Game extends SurfaceView implements Runnable {
    //Refering to this tutorial: https://gamecodeschool.com/android/coding-a-snake-game-for-android/

    //Game Control
    public boolean doGameLoop;
    public boolean isPaused;
    public boolean userPaused;
    private final int fps;

    //Graphics
    private Canvas canvas; //drawing happens here
    private SurfaceHolder surfaceHolder; //Actual visual
    protected final CustomDialog dialogBox;

    private final Paint fill; //https://stackoverflow.com/questions/36717782/how-to-fill-canvas-with-a-color
    //Used for "refreshing" a canvas
    public int screenX, screenY;

    //Used for adaptive scaling. Testing on the given screen resolution,
    private final int SCREENX_CONST = 1440;
    private final int SCREENY_CONST = 3120;
    private float scaleX, scaleY;

    //Gameplay
    Combat combat;
    public RoomVisual roomVisual;
    private RoomMaster roomMaster;
//    private DrawInstructions playerDrawInstructions;
    private final Arrows arrows;
    Enemy enemyToDraw = new Goblin("Jerry", 30);
    AnimatedSprite enemyAnim;
    //Sprites and stuff
    private Sprite healthBar = new Sprite(Assets.AssetID.HEALTH_BAR, 128, 32, 1);
    private AnimatedSprite restBench;

    private int oldX, oldY; //For player tracking and miniMap updates
    private MiniMap miniMap;
    public boolean showMiniMap = false;
    DrawInstructions playerDrawInstructions;
    //Other
    private final AppCompatActivity activity;

    //Other
    private GameTouchListener touchListener;
    //AnimatedSprites
    private AnimatedSprite playerSprite;

    private AnimatedSprite attackUp, attackDown, parry;
    private DrawInstructions attackUpInstruct, attackDownInstruct, parryInstruct;

    private ParticleSystem playerHit;
    private boolean confirmExitToScreen = false;
    private ElapseTime frameTime = new ElapseTime();

    @SuppressLint("SetTextI18n")

    public Game(AppCompatActivity activity, SurfaceHolder surfaceHolder, Point size, View gameView){
        super(activity);
        this.activity = activity;
        this.surfaceHolder = surfaceHolder;
        //Screen and UI
        fps = 1000/60;

        screenX = size.x;
        screenY = size.y;
        dialogBox = activity.findViewById(R.id.combat_dialog);

        scaleX = (float) screenX / SCREENX_CONST;
        scaleY = (float) screenY / SCREENY_CONST;
        System.out.println(scaleX +  ", " + scaleY);

        //Fixed screen Scaling on smaller devices
        this.surfaceHolder.setFixedSize((SCREENX_CONST),(SCREENY_CONST)); //This fixed the scaling issue for smaller devices

        fill = new Paint();
        fill.setStyle(Paint.Style.FILL);
        fill.setColor(Color.BLACK);

        arrows = activity.findViewById(R.id.arrows);

        touchListener = new GameTouchListener(this);
        gameView.setOnTouchListener(touchListener);

        DrawInstructions.phoneSizeX = screenX;
        DrawInstructions.phoneSizeY = screenY;
        DrawInstructions.clearDrawList();

        //Pausing
        isPaused = false; //pausing controlled by leaving app, etc.
        userPaused = false; //Pausing controlled by pause button
        showMiniMap = false;

        playerSprite = new AnimatedSprite(new Sprite(Assets.AssetID.PLAYER, 32, 32, 4));

        doGameLoop = true;
        OutputText.addDialogEventListener(this::dialogEventListener);

    }


    public void preparePlayer(){
        //Looks
        playerSprite.addAnimation(new Animation("Idle", 0, 3, new int[]{84, 84, 124, 400}));
        playerSprite.setCurrentAnimation("Idle");
        playerSprite.setCurrentRepeat(true);
        playerSprite.playCurrentAnimation();

        playerDrawInstructions = new DrawInstructions(575, 1500, playerSprite,Sprite.universalSpriteScale, Sprite.universalSpriteScale);

        //Gameplay
        Apple apple = new Apple();
        Potion potion = new Potion();

        QuickAttack quickAttack = new QuickAttack();
        Player player = roomMaster.getPlayer();
        player.addAttack(quickAttack);
        for (int i = 0; i < 5; i++){
            player.addItem(apple);
        }
        player.addItem(potion);
    }

    public void prepMiniMap(){

        miniMap = new MiniMap(roomMaster.getFloorMap(), 1000, 1000);
        int[] playerPos = {roomMaster.getCurrentRoom().getRoomId() % 100, roomMaster.getCurrentRoom().getRoomId() /100};

        MiniMap.playerX = playerPos[0];
        MiniMap.playerY = playerPos[1];
        oldX = MiniMap.playerX;
        oldY = MiniMap.playerY;

        miniMap.updateExploredMap(MiniMap.playerY,MiniMap.playerX);
        miniMap.makeMapVisual();
    }

    private void prepRestRoom() {
        restBench = new AnimatedSprite(new Sprite(Assets.AssetID.BENCH, 64, 32, 1));
        restBench.addAnimation(new Animation("Static", 0, 0, new int[] {0}));
        restBench.setCurrentAnimation("Static");
    }

    private boolean hasPlayerMoved(){
        return oldX != MiniMap.playerX || oldY != MiniMap.playerY;
    }
    private void mapUpdate(){
        if (hasPlayerMoved()){
            oldX = MiniMap.playerX;
            oldY = MiniMap.playerY;
            miniMap.updateCurrentMapWithPlayerPosition();
            miniMap.checkBossAdjacentAndMark();
            miniMap.makeMapVisual();
        }
    }

    private void playerHitParticleSetup(){
        playerHit = new ParticleSystem(30,100, 200, 8, 8);
        playerHit.respawnParticles = false;
        playerHit.createRectBaseParticle(Color.RED, 10, 10);

        playerHit.setParticleSettings(
                700, 700,
                1700, 1700,
                -10, 10,
                -10, 0,
                0, 0,
                -5, -5
        );

        playerHit.createAllParticles();
        for (int i = 0; i < 200; i ++)
            playerHit.updateParticles(); //Hide inital particles


    }
    private void attackFXSetup(){
        Sprite up = new Sprite(Assets.AssetID.SlASH_UP,32, 32, 9);
        Sprite down = new Sprite(Assets.AssetID.SLASH_DOWN,32, 32, 9);

        attackUp = new AnimatedSprite(up);
        attackDown = new AnimatedSprite(down);

        int[] timings = new int[] {50, 50, 50, 50, 50, 50, 50, 50, 50};

        attackUp.addAnimation(new Animation("attack", 0, 9, timings));
        attackDown.addAnimation(new Animation("attack", 0, 9, timings));
        attackUp.setCurrentAnimation("attack");
        attackDown.setCurrentAnimation("attack");


        attackUpInstruct = new DrawInstructions(575, 1200, attackUp, Sprite.universalSpriteScale, Sprite.universalSpriteScale);
        attackDownInstruct = new DrawInstructions(575, 1100, attackDown, Sprite.universalSpriteScale, Sprite.universalSpriteScale);


        Sprite parrySprite = new Sprite(Assets.AssetID.PARRY, 32, 32, 1);
        parry = new AnimatedSprite(parrySprite);
        parry.addAnimation(new Animation("parry", 0, 12, new int[] {40, 40, 40, 40, 150, 50, 60, 40, 70, 80, 90, 100}));
        parry.setCurrentAnimation("parry");


        parryInstruct = new DrawInstructions(575, 1200, parry, Sprite.universalSpriteScale, Sprite.universalSpriteScale);

    }

    @Override
    public void run() {

        preparePlayer();

        attackFXSetup();

        playerHitParticleSetup();

        prepMiniMap();

        prepRestRoom();

        //GameLoop happens Here
        while (doGameLoop){
            if (OutputText.isNewText()) {
                String text = OutputText.getOutputText();
                activity.runOnUiThread(() -> {setDialogText(text);});
            }
            combat.run(roomMaster.getCurrentRoom(), isPaused || userPaused);
            if (isPaused || userPaused) continue;
            if (frameTime.hasTimeElapsed(fps)) continue;
            mapUpdate();
            draw();
            playerHit.updateParticles();
        }

//        deathScreen();

//        activity.runOnUiThread(() ->{
//            ((MainActivity)activity).onQuit(findViewById(R.id.quit_button));
//        });
    }

    public void setDoGameLoop(boolean state){
        doGameLoop = state;
    }

    //Draw variables
    //HealthBar
    int magicHealthBarPositionX = 50;
    int magicHealthBarPositionY = 200;
    int magicHealthBarScaleX = 5;
    int magicHealthBarScaleY = 5;
    int actualBarX = magicHealthBarPositionX + 35 * magicHealthBarScaleX;
    int actualBarY = magicHealthBarPositionY + 6 * magicHealthBarScaleY;
    int actualBarWidth = 82 * magicHealthBarScaleX;
    int actualBarHeight = 20 * magicHealthBarScaleY;
    int healthBarMaxHPColor = Color.BLACK;
    int healthBarCurrentColor = Color.RED;

    /**
    Draw instructions for all visuals relevant to the game
     */
    /*
    Bug: Canvas Scaling is wierd on different sized devices, causing sprites to inflate and loss
    of onscreen canvas information
    Solution: made surface holder a fixed size of original development resolution, forcing it to not scale
    on smaller or bigger devices
    Fixed on: 2025-03-19
     */
    @SuppressLint("SetTextI18n")
    public void draw(){
        if (!surfaceHolder.getSurface().isValid()) return;//check surface is correct
        canvas = surfaceHolder.lockCanvas(); //get the current surface as a canvas object, prevent changes to surface
        ElapseTime.update(); // Update the current time
        Player player = roomMaster.getPlayer();
        Room currentRoom = roomMaster.getCurrentRoom();
        //Drawing
        canvas.drawPaint(fill); //Refresh the canvas
        if (roomVisual != null) //Room Drawing
            roomVisual.draw(canvas, (int)(-RoomVisual.getScaleX() * RoomVisual.getTilePixelWidth() * 0.5), 0);
            //Enemy Drawings
        enemyToDraw = currentRoom.getEnemy();

        if (enemyToDraw != null) {
            enemyToDraw.setEnemyAppearance();
            if (enemyToDraw.animatedSprite.doDraw) {
                enemyToDraw.animatedSprite.updateCurrentAnimation();
                enemyToDraw.animatedSprite.drawAnimation(canvas, 550, 800, Sprite.universalSpriteScale, Sprite.universalSpriteScale);
                drawHealthBar(canvas, 550, 700, enemyToDraw.getHealth(), enemyToDraw.getMaxHealth(),70 * magicHealthBarScaleX, 10 * magicHealthBarScaleY);
            }
        }

        drawBench(canvas);
        playerHit.drawAllParticles(canvas);
        //Entity Drawing
        DrawInstructions.drawAll(canvas);


        drawHealthBar(canvas, actualBarX, actualBarY, player.getHealth(), player.getMaxHealth(),actualBarWidth, actualBarHeight, healthBarMaxHPColor, healthBarCurrentColor);
        healthBar.drawScaled(canvas, magicHealthBarPositionX, magicHealthBarPositionY, magicHealthBarScaleX, magicHealthBarScaleY);

        if (showMiniMap)
            miniMap.drawToCanvas(canvas, 200, 1000);

        //Final Image updates

        surfaceHolder.unlockCanvasAndPost(canvas); //update the surface
    }

    public void onTouchEvent(float touchX, float touchY){
        if (!doGameLoop){
            confirmExitToScreen = true;
            return;
        }
        if (dialogBox.isTextFinishedUpdating() && OutputText.isInDialog()) {
            dialogBox.closeDialog();
            OutputText.setInDialog(false);
        }
        combat.screenTappedOn(touchX, touchY);
    }

    public void setDialogText(String text) {
        dialogBox.setText(text);
        OutputText.setInDialog(true);
    }

    /**
     * Helper for visualization of hp
     * @param c - canvas
     * @param x - positionX
     * @param y - positionY
     * @param currentHP - Current Hp of entity
     * @param maxHP - Max hp of entity
     * @param barWidth - Width of bar
     * @param barHeight - Height of bar
     * @param color1 - Color for Max Health
     * @param color2 - Color for Current Health
     */

    private void drawHealthBar(Canvas c, int x, int y, int currentHP, int maxHP, int barWidth, int barHeight, int color1, int color2){
        float currentPercent = ((float)currentHP)/maxHP;

        Paint p = new Paint();
        p.setColor(color1);
        c.drawRect(x, y, x + barWidth, y + barHeight, p);
        p.setColor(color2);
        c.drawRect(x, y, x + barWidth * currentPercent, y + barHeight, p);
    }

    private void drawHealthBar(Canvas c, int x, int y, int currentHP, int maxHP, int barWidth, int barHeight){
        drawHealthBar(c, x, y, currentHP, maxHP, barWidth, barHeight, Color.RED, Color.GREEN);
    }
    public void drawHealthBarAbove(Canvas c, DrawInstructions target, int currentHP, int maxHP, int color1, int color2){
        int magicWidth = 300; int magicHeight = 30;
        int magicYDisplace = -30; int magicXDisplace = -5;
        drawHealthBar(c, target.getX() + magicXDisplace, target.getY() + magicYDisplace, currentHP, maxHP, magicWidth, magicHeight, color1, color2);
    }

    public void drawHealthBarAbove(Canvas c, DrawInstructions target, int currentHP, int maxHP){
        drawHealthBarAbove(c, target, currentHP, maxHP, Color.RED, Color.GREEN);
    }

    public void setRoomVisual(RoomVisual roomVisual) {
        this.roomVisual = roomVisual;
    }

    public void setRoomMaster(RoomMaster roomMaster) {
        this.roomMaster = roomMaster;
        arrows.setRoomMaster(roomMaster);
        arrows.setArrows();
        combat = new Combat(activity, roomMaster.getPlayer(), fps);
        combat.setEventListener(this::combatEventListener);
        System.out.println(roomMaster);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private void drawBench(Canvas canvas) {
        if (roomMaster.getCurrentRoom() instanceof Rest) {
            restBench.drawAnimation(canvas, 411, 1500, Sprite.universalSpriteScale, Sprite.universalSpriteScale);
        }
    }

    public void disableArrows(Boolean visibility) {
        Room room = roomMaster.getCurrentRoom();
        if (!visibility) { // Before turning them on
            if ((room instanceof Encounter || room instanceof Boss) && combat.isInCombat()) return;
            if (room instanceof Rest && !room.getRoomCleared()) return;
        }
        arrows.disable(visibility);

    }

    private void combatEventListener(Controller.CombatEventEnum event) {
        switch (event) {
            case PLAYER_ATTACK: {
                attackUp.playCurrentAnimation();
                break;
            }
            case ENEMY_ATTACK: {
                attackDown.playCurrentAnimation();
                playerHit.resetAllParticles();
                break;
            }
            case PLAYER_DEATH: {
                Log.d("Game", "Player Died");
                //TODO: Kick user back to menu
                doGameLoop = false;

                break;
            }
            case PLAYER_PARRY: {
                parry.playCurrentAnimation();
                break;
            }
            case ENEMY_DEATH: {
                Log.d("Game", "Enemy Died");
                roomMaster.getCurrentRoom().setRoomCleared(true);
                //Generate new floor after boss is defeated
                if (roomMaster.getCurrentRoom() instanceof Boss){
                    roomMaster.setCurrentFloor(roomMaster.getCurrentFloor() + 1);
                    activity.runOnUiThread(() -> {
                        roomMaster.generateRooms(5, 5, 10);
                        roomMaster.moveToRoom(roomMaster.getCurrentRoom());
                        prepMiniMap();
                        mapUpdate();
                        arrows.setArrows();
                        resetEntities();
                    });
                }
                break;
            }
            case PARRY_WINDOW_OPEN: {
                System.out.println("Parry Window Open");
                break;
            }
            case PARRY_WINDOW_CLOSE: {
                System.out.println("Parry Window Closed");
                break;
            }
            default:{
                //Do nothing
            }
        }
    }

    private void dialogEventListener(OutputText.DialogEvent event) {
        switch (event) {
            case CLOSED: {
                disableArrows(false);
                break;
            }
            default: {
                //Do nothing
            }
        }
    }

    private void resetEntities(){
        DrawInstructions.thingsToDraw.clear();

        DrawInstructions.thingsToDraw.add(playerDrawInstructions);
        DrawInstructions.thingsToDraw.add(attackDownInstruct);
        DrawInstructions.thingsToDraw.add(attackUpInstruct);
        DrawInstructions.thingsToDraw.add(parryInstruct);
    }

    private void deathScreen(){
        canvas = surfaceHolder.lockCanvas();
        Paint black = new Paint();
        black.setColor(Color.BLACK);
        canvas.drawRect(300, 300, screenX - 300, screenY - 300, black);
        Paint text = new Paint();
        text.setColor(Color.WHITE);
        text.setTextSize(100);

        canvas.drawText("You Died!", 575, 1300, text);
        text.setTextSize(50);
        canvas.drawText("Tap Anywhere to return to main menu", 200, 1600, text);
        surfaceHolder.unlockCanvasAndPost(canvas);

        arrows.hideArrows();

        while (!confirmExitToScreen){

        }

    }


    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public void endGame() {
        doGameLoop = false;
    }
}



