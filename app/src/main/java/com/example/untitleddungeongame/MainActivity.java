package com.example.untitleddungeongame;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Declarations
    SurfaceView gameView;

    MyCallBack myCallBack;

    GameLoop gameControl;

    HashMap<String, Bitmap> assets;
    boolean gameLaunched;

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
    public void onStart(){
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

        gameControl = myCallBack.getGame();
        gameLaunched = true;

    }

    @Override
    protected void onPause(){

        if (gameControl != null){
            gameControl.isPaused = true;
        }

        super.onPause();

    }

    @Override
    protected void onResume(){

        if (gameControl != null){
            gameControl.isPaused = false;
            myCallBack.reStartGame();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy(){
//        gameControl.setDoGameLoop(false);
        super.onDestroy();
        System.out.println("DESTROYED GAME");


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void importAssets(){

        assets = new HashMap(10);
        Resources resources = getResources();

        //TODO: Migrate keys and image values to a json or xml file, then loop through to create assets
        assets.put("playerRouge", BitmapFactory.decodeResource(resources, R.drawable.playerrouge));







    }


}