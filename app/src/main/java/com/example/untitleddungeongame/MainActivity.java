/**
 * To Do for deployment:
 *      TestCases (Meaningful)
 *      Bug Documentation
 *
 */



package com.example.untitleddungeongame;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.untitleddungeongame.floors.RoomMaster;
import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.handlers.Game;
import com.example.untitleddungeongame.hotbar.HotBarInfo;
import com.example.untitleddungeongame.hotbar.items.Item;
import com.example.untitleddungeongame.ui.ConfirmCancelMenu;
import com.example.untitleddungeongame.ui.PauseMenu;
import com.example.untitleddungeongame.ui.PixelButton;
import com.example.untitleddungeongame.ui.hotbar.HotBar;
import com.example.untitleddungeongame.ui.shopbar.ShopBar;
import com.example.untitleddungeongame.ui.swapbar.SwapBar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //Declarations
    SurfaceView gameView;
    private boolean gameLaunched;
    private PixelButton pauseButton;
    private PixelButton miniMapButton;
    private PauseMenu pauseMenu;
    private ConfirmCancelMenu confirmCancel;
    private Player player;
    private MyCallBack gameCallBack;
    private Game game;

    //HashMap<AssetID, Bitmap> assets;
    RoomMaster roomMaster;
    final int STARTING_ROWS = 5;
    final int STARTING_COLS = 5;
    final int STARTING_THRESHOLD = (int) (STARTING_ROWS * STARTING_COLS * 0.6);
    private ShopBar<Item> shopBar;
    private SwapBar<Item> swapBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        gameView = findViewById(R.id.gameView);
        pauseButton = findViewById(R.id.pause_button);
        pauseMenu = findViewById(R.id.pause_menu);
        miniMapButton = findViewById(R.id.map_button);
        confirmCancel = findViewById(R.id.confirm_cancel);
        shopBar = findViewById(R.id.shop_bar);
        swapBar = findViewById(R.id.swap_bar);

        player = new Player(20, 6, 5, 10);
        roomMaster = new RoomMaster(player);

        confirmCancel.setRoomMaster(roomMaster);
        confirmCancel.hide();

        roomMaster.setConfirmCancel(confirmCancel);
        roomMaster.setShopBar(shopBar);
        roomMaster.setSwapBar(swapBar);
        gameCallBack = new MyCallBack(this, gameView, roomMaster);
        EdgeToEdge.enable(this);
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


        pauseButton.setOnClickListener(this::onUserPause);
        pauseMenu.setOnQuitClickListener(this::onQuit);
        pauseMenu.setOnResumeClickListener(this::onUserResume);
        miniMapButton.setOnClickListener(this::mapButton);
        pauseMenu.disable(true);

        gameView.getHolder().addCallback(gameCallBack);
        gameLaunched = true;

        roomMaster.generateRooms(STARTING_ROWS, STARTING_COLS, STARTING_THRESHOLD);
    }

    /**
     * Note that this is when the user minimizes the game, i.e. presses home
     */
    @Override
    protected void onPause(){
        super.onPause();
        if (gameCallBack == null) return;
        Game currentGame = gameCallBack.getGame();
        if (currentGame == null) return;
        synchronized (currentGame) {
            currentGame.pause();
            pauseMenu.disable(false);
        }
    }

    /**
     * Note that this is when the user reopons the game, i.e. navigates back to the game from the
     * overview button
     */
    @Override
    protected void onResume(){
        super.onResume();
        if (gameCallBack == null) return;
        Game currentGame = gameCallBack.getGame();
        if (currentGame == null) return;
        synchronized (currentGame) {
            currentGame.resume();
            pauseMenu.disable(true);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        endGame();
        pauseMenu.setVisibility(View.GONE);
    }

    public void onUserResume(View v){
        if (gameCallBack == null) return;
        Game currentGame = gameCallBack.getGame();
        if (currentGame == null) return;
        synchronized (currentGame) {
            currentGame.resume();
            currentGame.disableArrows(false);
            pauseMenu.disable(true);
            pauseButton.setVisibility(VISIBLE);
            miniMapButton.setVisibility(VISIBLE);
        }
    }
    public void onUserPause(View v){
        if (gameCallBack == null) return;
        Game currentGame = gameCallBack.getGame();
        if (currentGame == null) return;
        synchronized (currentGame) {
            currentGame.pause();
            currentGame.showMiniMap = false;
            currentGame.disableArrows(true);
            pauseMenu.disable(false);
            pauseButton.setVisibility(INVISIBLE);
            miniMapButton.setVisibility(INVISIBLE);
        }
    }

    public void onQuit(View v){
        endGame();
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void restartGame(View v){
        endGame();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void backToMainMenu(View v){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void mapButton(View v){
        if (gameCallBack == null) return;
        Game currentGame = gameCallBack.getGame();
        if (currentGame == null) return;
        synchronized (currentGame) {
            currentGame.showMiniMap = !currentGame.showMiniMap;
            currentGame.disableArrows(currentGame.showMiniMap);
        }
    }

    private void endGame(){
        Game currentGame = gameCallBack.getGame();
        if (currentGame == null) return;
        synchronized (currentGame) {
            currentGame.doGameLoop = false;
        }
    }

    public ShopBar<Item> getShopBar() {
        return shopBar;
    }

    public SwapBar<Item> getSwapBar() {
        return swapBar;
    }
}