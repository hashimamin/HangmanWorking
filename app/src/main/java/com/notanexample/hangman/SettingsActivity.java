package com.notanexample.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class SettingsActivity extends AppCompatActivity {
    RelativeLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //set background color of activity
        parentLayout = findViewById(R.id.parentLayout);
        setBackgroundColor();

    }

    public void onRadioButtonClicked(View view) {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();

        switch (view.getId()) {
            case R.id.white:
                editor.putString("background", "white");
                editor.apply();
                break;
            case R.id.red:
                editor.putString("background", "red");
                editor.apply();
                break;
            case R.id.green:
                editor.putString("background", "green");
                editor.apply();
                break;
            case R.id.blue:
                editor.putString("background", "blue");
                editor.apply();
                break;
        }


    }

    private void setBackgroundColor() {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String color = settings.getString("background", "white");

        switch (color) {
            case "white":
                parentLayout.setBackgroundColor(Color.WHITE);
                break;
            case "red":
                parentLayout.setBackgroundColor(Color.RED);
                break;
            case "green":
                parentLayout.setBackgroundColor(Color.GREEN);
                break;
            case "blue":
                parentLayout.setBackgroundColor(Color.BLUE);
                break;
        }

    }

}
