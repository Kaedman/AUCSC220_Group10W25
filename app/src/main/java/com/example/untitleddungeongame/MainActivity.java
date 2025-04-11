/**
 * To Do for deployment:
 *      TestCases (Meaningful)
 *      Bug Documentation
 *
 */



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
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.untitleddungeongame.floors.Boss;
import com.example.untitleddungeongame.floors.RoomMaster;
import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.handlers.Game;
import com.example.untitleddungeongame.ui.Arrows;
import com.example.untitleddungeongame.ui.PauseMenu;

public class MainActivity extends AppCompatActivity {

    //Declarations
    SurfaceView gameView;
    public boolean gameLaunched;
    public Button pauseButton;

    PauseMenu pauseMenu;
    ImageView resumeVisual;
    ImageView quitVisual;
    ImageView attacksVisual;
    ImageView itemsVisual;

    TextView resumeText;
    TextView quitText;
    TextView attacksText;
    TextView itemsText;

    MyCallBack myCallBack;
    Player player;
    Game gameControl;
    static boolean userPause;
    Arrows arrows;

    //HashMap<AssetID, Bitmap> assets;
    RoomMaster roomMaster;
    final int STARTING_ROWS = 5;
    final int STARTING_COLS = 5;
    final int STARTING_THRESHOLD = (int) (STARTING_ROWS * STARTING_COLS * 0.8);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        importAssets();
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

        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size); //Instead of returning a value, we need to specify a point variable to change

        //Initializations Work Goes here
        gameView = findViewById(R.id.gameView);
        myCallBack = new MyCallBack(this, gameView);
        //myCallBack = new MyCallBack(this, gameView, assets);
        gameView.getHolder().addCallback(myCallBack);

        gameLaunched = true;
        //Log.d("gameMain", myCallBack.getGame().toString());

        player = new Player(10);
        roomMaster = new RoomMaster(player);
        myCallBack.setRoomMaster(roomMaster);
        roomMaster.generateRooms(STARTING_ROWS, STARTING_COLS, STARTING_THRESHOLD);

        arrows = findViewById(R.id.arrows);
        arrows.setRoomMaster(roomMaster);
        arrows.setArrows();

        attacksText = findViewById(R.id.attacks_text);
        itemsText = findViewById(R.id.items_text);

        pauseButton = findViewById(R.id.pause);
        pauseButton.setAlpha(0.0f);
        pauseMenu = findViewById(R.id.pause_menu);
        pauseMenu.disable(true);

        pauseMenu.setOnQuitClickListener(this::onQuit);
        pauseMenu.setOnResumeClickListener(this::onUserResume);
    }

    /**
     * Note that this is when the user minimizes the game, i.e. presses home
     */
    @Override
    protected void onPause(){
        Game.isPaused = true;
        pauseMenu.disable(false);
        super.onPause();
    }

    /**
     * Note that this is when the user reopons the game, i.e. navigates back to the game from the
     * overview button
     */
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

        //assets = new HashMap<AssetID, Bitmap>(10);
        Resources resources = getResources();

        //TODO: Migrate keys and image values to a json or xml file, then loop through to create assets
        Assets.addAsset(Assets.AssetID.PLAYER, BitmapFactory.decodeResource(resources, R.drawable.playerrouge));
        Assets.addAsset(Assets.AssetID.ENEMY_SLIME, BitmapFactory.decodeResource(resources, R.drawable.enemyslime));
        Assets.addAsset(Assets.AssetID.TILESET, BitmapFactory.decodeResource(resources, R.drawable.tiles));

        Assets.addAsset(Assets.AssetID.CHESTS, BitmapFactory.decodeResource(resources, R.drawable.chests)); //chests may be updated to show opened state
        Assets.addAsset(Assets.AssetID.ITEM_HEAL, BitmapFactory.decodeResource(resources, R.drawable.itemsheals));
        Assets.addAsset(Assets.AssetID.ITEM_OFFENSE, BitmapFactory.decodeResource(resources, R.drawable.itemsoffense));
        Assets.addAsset(Assets.AssetID.ITEM_SLOT, BitmapFactory.decodeResource(resources, R.drawable.itemslot));

        Assets.addAsset(Assets.AssetID.BUTTON, BitmapFactory.decodeResource(resources, R.drawable.button));
        Assets.addAsset(Assets.AssetID.BUTTON_PRESSED, BitmapFactory.decodeResource(resources, R.drawable.buttonpressed));
        Assets.addAsset(Assets.AssetID.BUTTON_PAUSE, BitmapFactory.decodeResource(resources, R.drawable.buttonpause));
        Assets.addAsset(Assets.AssetID.BUTTON_INVENTORY, BitmapFactory.decodeResource(resources, R.drawable.buttoninventory));

        Assets.addAsset(Assets.AssetID.DIALOG_FRAME, BitmapFactory.decodeResource(resources, R.drawable.dialog_frame));
        Assets.addAsset(Assets.AssetID.HEALTH_BAR, BitmapFactory.decodeResource(resources, R.drawable.healthbar));
    }

    public void onUserResume(View v){
        Game.userPaused = false;
        pauseMenu.disable(true);
        arrows.setArrows();
        pauseButton.setVisibility(View.VISIBLE);
        System.out.println("Resumed");
    }
    public void onUserPause(View v){
        Game.userPaused = true;
        pauseMenu.disable(false);
        arrows.hideArrows();
        System.out.println("Paused");
    }

    public void onQuit(View v){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void setRoomMasterGame(Game game) {
        roomMaster.setGame(game);
    }

    public void mapButton(View v){
        Game.showMiniMap = !Game.showMiniMap;
    }
}