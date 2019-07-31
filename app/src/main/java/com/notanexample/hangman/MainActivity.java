package com.notanexample.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        nightMode();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Play Button: Go to ChooseCategoryActivity
        Button playGameButton = (Button) findViewById(R.id.playGameButton);
        playGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent categoryActivity = new Intent(getApplicationContext(), ChooseCategoryActivity.class);
                startActivity(categoryActivity);
            }
        });

        //Highscore Button: Go to HighscoreActivity
        Button highscoreButton = (Button) findViewById(R.id.highscoreButton);
        highscoreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent highscoreActivity = new Intent(getApplicationContext(), HighscoreActivity.class);
                startActivity(highscoreActivity);
            }
        });

        //SettingsActivity Button: Go to SettingsActivity
        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent settingsActivity = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsActivity);
            }
        });

        //Quit Button: Quits app
        Button quitButton = (Button) findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.exit(0);
            }
        });

        updateStats();
    }

    @Override
    public void onRestart(){
        super.onRestart();

        updateStats();
    }


    // Set stats text
    void updateStats() {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        int wins = settings.getInt("wins", 0);
        int loses = settings.getInt("loses", 0);

        TextView winsText = (TextView) findViewById(R.id.winsText);
        winsText.setText("Games Won: "+wins);

        TextView losesText = (TextView) findViewById(R.id.losesText);
        losesText.setText("Games Lost: "+loses);
    }

    public void nightMode() {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
<<<<<<< Updated upstream
        String color = settings.getString("background", "white");

        getWindow().setNavigationBarColor(Color.parseColor("#341f97"));

        ArrayList<View> layoutButtons = parentLayout.getTouchables();
        for (View v : layoutButtons) {
            if (v instanceof Button) {
                ((Button) v).setBackgroundColor(Color.parseColor("#5f27cd"));
                ((Button) v).setHighlightColor(Color.parseColor("#341f97"));
                ((Button) v).setTextColor(Color.parseColor("#FFFFFF"));
            }
        }

        switch (color) {
            case "white":
                parentLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case "red":
                parentLayout.setBackgroundColor(Color.parseColor("#ff6b6b"));
                break;
            case "green":
                parentLayout.setBackgroundColor(Color.parseColor("#1dd1a1"));
                break;
            case "blue":
                parentLayout.setBackgroundColor(Color.parseColor("#48dbfb"));
                break;
        }
=======
        boolean isNightMode = settings.getBoolean("nightmode", false);
>>>>>>> Stashed changes

        if(isNightMode) {
            setTheme(R.style.DarkTheme);
        }
        else
            setTheme(R.style.AppTheme);
    }

}
