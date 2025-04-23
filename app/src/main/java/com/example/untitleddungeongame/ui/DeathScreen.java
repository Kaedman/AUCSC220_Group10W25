package com.example.untitleddungeongame.ui;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.untitleddungeongame.MainActivity;
import com.example.untitleddungeongame.MainMenu;
import com.example.untitleddungeongame.R;

public class DeathScreen extends androidx.constraintlayout.widget.ConstraintLayout{

    private final Context context;

    private TextView deathText, newRunText, backToMainMenuText;
    private ImageView newRun, mainMenu;

    private MainActivity mainActivity;


    public DeathScreen(Context c, AttributeSet attributeSet){
        super(c, attributeSet);
        context = c;
        init();
    }

    public void init(){
        LayoutInflater.from(context).inflate(R.layout.death_screen, this, true);
        newRun = findViewById(R.id.retry);
        mainMenu = findViewById(R.id.backToMainMenu);

        deathText = findViewById(R.id.youDiedText);
        newRunText = findViewById(R.id.deathNewRun);
        backToMainMenuText = findViewById(R.id.deathMainMenu);

        newRun.setOnClickListener(this :: restartGame);
        mainMenu.setOnClickListener(this :: returnToMainMenu);

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

    public void restartGame(View v){
        mainActivity.restartGame(v);
    }

    public void returnToMainMenu(View v){
        mainActivity.onQuit(v);
    }

    public void setMainActivity(MainActivity m) {
        mainActivity = m;
    }
}
