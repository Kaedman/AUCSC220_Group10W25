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
import com.example.untitleddungeongame.handlers.Game;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Declarations
    SurfaceView gameView;
    public boolean gameLaunched;
    public Button resumeButton;
    public Button quitButton;
    public Button pauseButton;
    public ImageView leftArrow;
    public ImageView rightArrow;
    public ImageView upArrow;
    public ImageView downArrow;

    MyCallBack myCallBack;

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

        roomMaster = new RoomMaster();
        //roomMaster.generateRooms(STARTING_ROWS, STARTING_COLS, STARTING_THRESHOLD);

        resumeButton = findViewById(R.id.resume);
        quitButton = findViewById(R.id.quit);
        pauseButton = findViewById(R.id.pause);

        resumeButton.setVisibility(View.GONE);
        quitButton.setVisibility(View.GONE);

        leftArrow = findViewById(R.id.left_arrow);
        rightArrow = findViewById(R.id.right_arrow);
        upArrow = findViewById(R.id.up_arrow);
        downArrow = findViewById(R.id.down_arrow);
        //setArrows();
    }

    @Override
    protected void onPause(){
        Game.isPaused = true;
        super.onPause();

    }

    @Override
    protected void onResume(){
        Game.isPaused = false;
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        System.out.println("DESTROYED GAME");

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

    public void setArrows() {
        Log.d("currroom", roomMaster.getCurrentRoom().getLeft().toString());
        setArrowActivated(leftArrow, roomMaster.getCurrentRoom().getLeft() != null);

        setArrowActivated(rightArrow, roomMaster.getCurrentRoom().getRight() != null);

        setArrowActivated(upArrow, roomMaster.getCurrentRoom().getUp() != null);

        setArrowActivated(downArrow, roomMaster.getCurrentRoom().getDown() != null);
    }

    public void onResume(View v){
        Game.userPaused = false;
        resumeButton.setVisibility(View.GONE);
        quitButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
        System.out.println("Resumed");
    }
    public void onPause(View v){
        Game.userPaused = true;
        resumeButton.setVisibility(View.VISIBLE);
        quitButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.GONE);
        System.out.println("Paused");
    }

    public void onQuit(View v){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);

    }

    public void onConfirm(View v) {
        if (roomMaster.getCurrentRoom() instanceof Rest) {
            ((Rest) roomMaster.getCurrentRoom()).useRest();
        } else if (roomMaster.getCurrentRoom() instanceof Boss) {

        }
    }

    public void moveLeft(View v) {
        setArrowActivated(leftArrow,false);
        //roomMaster.moveToRoom(roomMaster.getCurrentRoom().getLeft());
    }

    public void moveRight(View v) {
        setArrowActivated(rightArrow,false);
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getRight());
    }

    public void moveUp(View v) {
        setArrowActivated(upArrow,false);
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getUp());
    }

    public void moveDown(View v) {
        setArrowActivated(downArrow,false);
        roomMaster.moveToRoom(roomMaster.getCurrentRoom().getDown());
    }

    public void setArrowActivated(ImageView v, boolean activated) {
        Log.d("arrowId", v.getResources().getResourceName(v.getId()));
        if (activated) {
            v.setEnabled(true);

            switch (v.getResources().getResourceName(v.getId())) {
                case "com.example.untitleddungeongame:id/left_arrow":
                    v.setImageResource(R.drawable.confirmationarrow3);
                    break;
                case "com.example.untitleddungeongame:id/right_arrow":
                    v.setImageResource(R.drawable.confirmationarrow1);
                    break;
                case "com.example.untitleddungeongame:id/up_arrow":
                    v.setImageResource(R.drawable.confirmationarrow4);
                    break;
                case "com.example.untitleddungeongame:id/down_arrow":
                    v.setImageResource(R.drawable.confirmationarrow2);
                    break;
            }
        } else {
            v.setEnabled(false);

            switch (v.getResources().getResourceName(v.getId())) {
                case "com.example.untitleddungeongame:id/left_arrow":
                    v.setImageResource(R.drawable.shadedarrow3);
                    break;
                case "com.example.untitleddungeongame:id/right_arrow":
                    v.setImageResource(R.drawable.shadedarrow1);
                    break;
                case "com.example.untitleddungeongame:id/up_arrow":
                    v.setImageResource(R.drawable.shadedarrow4);
                    break;
                case "com.example.untitleddungeongame:id/down_arrow":
                    v.setImageResource(R.drawable.shadedarrow2);
                    break;
            }
        }
    }
}