package com.example.untitleddungeongame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.untitleddungeongame.Floors.Boss;
import com.example.untitleddungeongame.Floors.Rest;
import com.example.untitleddungeongame.Floors.RoomMaster;
import com.example.untitleddungeongame.animations.AssetID;
import com.example.untitleddungeongame.characters.Player;
import com.example.untitleddungeongame.handlers.Game;
import com.example.untitleddungeongame.ui.Arrows;
import com.example.untitleddungeongame.ui.CustomDialog;
import com.example.untitleddungeongame.ui.PauseMenu;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Declarations
    SurfaceView gameView;
    public boolean gameLaunched;


    PauseMenu pauseMenu;
    Button pauseButton;

    MyCallBack myCallBack;
    Player player;
    Game gameControl;
    static boolean userPause;

    HashMap<AssetID, Bitmap> assets;
    RoomMaster roomMaster;
    final int STARTING_ROWS = 5;
    final int STARTING_COLS = 5;
    final int STARTING_THRESHOLD = (int) (STARTING_ROWS * STARTING_COLS * 0.8);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        gameLaunched = false;
    }

    @Override
    protected void onStart(){
        super.onStart();

        importAssets();
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size); //Instead of returning a value, we need to specify a point variable to change

        //Initializations Work Goes here
        gameView = findViewById(R.id.gameView);

        myCallBack = new MyCallBack(this, gameView, assets);
        gameView.getHolder().addCallback(myCallBack);

        gameLaunched = true;
        System.out.println(gameControl);

        player = new Player(10);

        roomMaster = new RoomMaster(player);
        roomMaster.generateRooms(STARTING_ROWS, STARTING_COLS, STARTING_THRESHOLD);

        Arrows arrows = findViewById(R.id.arrows);
        arrows.setRoomMaster(roomMaster);
        arrows.setArrows();

        pauseButton = findViewById(R.id.pause);
        pauseButton.setAlpha(0.0f);
        pauseMenu = findViewById(R.id.pause_menu_main);
        pauseMenu.disable(true);

        pauseMenu.setOnQuitClickListener(this::onQuit);
        pauseMenu.setOnResumeClickListener(this::onResume);

    }

    @Override
    protected void onPause(){
        Game.isPaused = true;
        pauseMenu.disable(false);
        super.onPause();

    }

    @Override
    protected void onResume(){
        Game.isPaused = false;
        pauseMenu.disable(true);
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        System.out.println("DESTROYED GAME");
        pauseMenu.setVisibility(View.GONE);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void importAssets() {

        assets = new HashMap<AssetID, Bitmap>(10);
        Resources resources = getResources();

        //TODO: Migrate keys and image values to a json or xml file, then loop through to create assets
        assets.put(AssetID.PLAYER, BitmapFactory.decodeResource(resources, R.drawable.playerrouge));
        assets.put(AssetID.ENEMY_SLIME, BitmapFactory.decodeResource(resources, R.drawable.enemyslime));
        assets.put(AssetID.TILESET, BitmapFactory.decodeResource(resources, R.drawable.tiles));

        assets.put(AssetID.CHESTS, BitmapFactory.decodeResource(resources, R.drawable.chests)); //chests may be updated to show opened state
        assets.put(AssetID.ITEM_HEAL, BitmapFactory.decodeResource(resources, R.drawable.itemsheals));
        assets.put(AssetID.ITEM_OFFENSE, BitmapFactory.decodeResource(resources, R.drawable.itemsoffense));
        assets.put(AssetID.ITEM_SLOT, BitmapFactory.decodeResource(resources, R.drawable.itemslot));

        assets.put(AssetID.BUTTON_PAUSE, BitmapFactory.decodeResource(resources, R.drawable.buttonpause));
        assets.put(AssetID.BUTTON_INVENTORY, BitmapFactory.decodeResource(resources, R.drawable.buttoninventory));

        assets.put(AssetID.HEALTH_BAR, BitmapFactory.decodeResource(resources, R.drawable.healthbar));
    }


    public void onResume(View v){
        Game.userPaused = false;
        pauseMenu.disable(true);
        pauseButton.setVisibility(View.VISIBLE);
        System.out.println("Resumed");
    }
    public void onPause(View v){
        Game.userPaused = true;
        pauseMenu.disable(false);
        pauseButton.setVisibility(View.GONE);
        System.out.println("Paused");
    }

    public void onQuit(View v){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);

    }

    public void enterBoss() {
        ((Boss) roomMaster.getCurrentRoom()).updateRoom();
    }
}