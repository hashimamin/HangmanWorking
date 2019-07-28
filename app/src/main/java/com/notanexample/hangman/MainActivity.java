package com.notanexample.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
