package com.example.untitleddungeongame.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.untitleddungeongame.R;

public class DeathScreen extends androidx.constraintlayout.widget.ConstraintLayout{

    private Context context;

    private TextView deathText, newRunText, backToMainMenuText;
    private ImageView newRun, mainMenu;

    public DeathScreen(Context c){
        super(c);
        context = c;
        init();
    }

    public void init(){
        LayoutInflater.from(context).inflate(R.layout.arrow_container, this, true);
        newRun = findViewById(R.id.retry);
        mainMenu = findViewById(R.id.backToMainMenu);

        deathText = findViewById(R.id.youDiedText);
        newRunText = findViewById(R.id.deathNewRun);
        backToMainMenuText = findViewById(R.id.deathMainMenu);

        hide();

    }

    public void show(){
        newRun.setVisibility(VISIBLE);
        mainMenu.setVisibility(VISIBLE);

        deathText.setVisibility(VISIBLE);
        newRunText.setVisibility(VISIBLE);
        backToMainMenuText.setVisibility(VISIBLE);


    }

    public void hide(){
        newRun.setVisibility(GONE);
        mainMenu.setVisibility(GONE);

        deathText.setVisibility(GONE);
        newRunText.setVisibility(GONE);
        backToMainMenuText.setVisibility(GONE);

    }
}
