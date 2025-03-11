package com.example.untitleddungeongame;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Declarations


    SurfaceView gameView;
    Thread gameThread;
    GameLoop game;

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

    }


    //https://stackoverflow.com/questions/11490711/android-holder-getsurface-always-return-null
    //Need to make sure surface is initated
    public class MyCallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            //start code
            game.setSurfaceHolder(gameView.getHolder());
            gameThread = new Thread(game);
            gameThread.start();


        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
            //end code

        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size); //Instead of returning a value, we need to specify a point variable to change


        //Initializations Work Goes here
        gameView = findViewById(R.id.gameView);

        //Need the apps context, as for drawing, the gameLoop is our target display
        game = new GameLoop(this, size);

        game.setDoGameLoop(true);



    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        game.setDoGameLoop(false);

    }


}