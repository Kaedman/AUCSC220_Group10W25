package com.example.untitleddungeongame;

import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;

import androidx.activity.EdgeToEdge;
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
        //Initializations Work Goes here
        gameView = findViewById(R.id.gameView);


        //Need the apps context, as for drawing, the gameLoop is our target display
        game = new GameLoop(this, gameView);



//        setContentView(game);

        gameThread = new Thread(game);
        gameThread.start();

    }
}