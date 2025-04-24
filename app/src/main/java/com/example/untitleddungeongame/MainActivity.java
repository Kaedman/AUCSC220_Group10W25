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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.untitleddungeongame.floors.RoomMaster;
import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.handlers.Game;
import com.example.untitleddungeongame.ui.ConfirmCancelMenu;
import com.example.untitleddungeongame.ui.PauseMenu;
import com.example.untitleddungeongame.ui.PixelButton;

public class MainActivity extends AppCompatActivity {

    //Declarations
    SurfaceView gameView;
    private boolean gameLaunched;
    private PixelButton pauseButton;
    private PixelButton miniMapButton;
    private PauseMenu pauseMenu;
    private ConfirmCancelMenu confirmCancel;
    private Player player;

    //HashMap<AssetID, Bitmap> assets;
    RoomMaster roomMaster;
    final int STARTING_ROWS = 5;
    final int STARTING_COLS = 5;
    final int STARTING_THRESHOLD = (int) (STARTING_ROWS * STARTING_COLS * 0.6);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        gameView = findViewById(R.id.gameView);
        pauseButton = findViewById(R.id.pause_button);
        pauseMenu = findViewById(R.id.pause_menu);
        miniMapButton = findViewById(R.id.map_button);
        confirmCancel = findViewById(R.id.confirm_cancel);

        player = new Player(10);
        roomMaster = new RoomMaster(player);

        confirmCancel.setRoomMaster(roomMaster);
        confirmCancel.hide();

        pauseButton.setOnClickListener(this::onUserPause);
        pauseMenu.setOnQuitClickListener(this::onQuit);
        pauseMenu.setOnResumeClickListener(this::onUserResume);
        miniMapButton.setOnClickListener(this::mapButton);
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
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size); //Instead of returning a value, we need to specify a point variable to change

        pauseMenu.disable(true);

        gameView.getHolder().addCallback( new MyCallBack(this, gameView, roomMaster));

        gameLaunched = true;


        roomMaster.generateRooms(STARTING_ROWS, STARTING_COLS, STARTING_THRESHOLD);


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



    public void onUserResume(View v){
        System.out.println("Resumed");
        Game.userPaused = false;
        pauseMenu.disable(true);
        pauseButton.setVisibility(VISIBLE);
        miniMapButton.setVisibility(VISIBLE);
        System.out.println("Resumed");
    }
    public void onUserPause(View v){
        Game.userPaused = true;
        pauseMenu.disable(false);
        pauseButton.setVisibility(INVISIBLE);
        miniMapButton.setVisibility(INVISIBLE);
        System.out.println("Paused");
    }

    public void onQuit(View v){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void restartGame(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void backToMainMenu(View v){
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