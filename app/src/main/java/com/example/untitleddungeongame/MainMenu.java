package com.example.untitleddungeongame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.untitleddungeongame.ui.PixelButton;

public class MainMenu extends AppCompatActivity {
    TextView loadingText;
    PixelButton playButton;
    PixelButton newButton;
    PixelButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Assets.importAssets(getResources());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        playButton = findViewById(R.id.play_button);
        newButton = findViewById(R.id.new_run_button);
        settingsButton = findViewById(R.id.settings_button);

        loadingText = findViewById(R.id.loading_text);
        loadingText.setVisibility(View.GONE);

        playButton.setOnClickListener(this::buttonPlay);
        newButton.setOnClickListener(this::buttonPlay);
        settingsButton.setOnClickListener(this::buttonSettings);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void buttonPlay(View v){
        loadingText.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void buttonSettings(View v){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}