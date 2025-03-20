package com.example.untitleddungeongame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class mainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu);

    }

    public void buttonPlay(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}