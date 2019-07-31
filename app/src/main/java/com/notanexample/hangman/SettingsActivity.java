package com.notanexample.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    Switch nightModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        nightMode();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //set night mode on/off
        nightModeSwitch = findViewById(R.id.nightModeSwitch);

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        boolean isNightMode = settings.getBoolean("nightmode", false);
        if(isNightMode)
            nightModeSwitch.setChecked(true);

        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("nightmode", true);
                    editor.apply();

                    recreate();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("nightmode", false);
                    editor.apply();

                    recreate();
                }
            }
        });



        //set background color of activity
//        parentLayout = findViewById(R.id.parentLayout);
//        setBackgroundColor();

    }

    public void nightMode() {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
<<<<<<< Updated upstream
        String color = settings.getString("background", "white");

        getWindow().setNavigationBarColor(Color.parseColor("#341f97"));

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
=======
        boolean isNightMode = settings.getBoolean("nightmode", false);

        if(isNightMode) {
            setTheme(R.style.DarkTheme);
>>>>>>> Stashed changes
        }
        else
            setTheme(R.style.AppTheme);

    }

//    public void onRadioButtonClicked(View view) {
//        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
//        SharedPreferences.Editor editor = settings.edit();
//
//        switch (view.getId()) {
//            case R.id.white:
//                editor.putString("background", "white");
//                editor.apply();
//                break;
//            case R.id.red:
//                editor.putString("background", "red");
//                editor.apply();
//                break;
//            case R.id.green:
//                editor.putString("background", "green");
//                editor.apply();
//                break;
//            case R.id.blue:
//                editor.putString("background", "blue");
//                editor.apply();
//                break;
//        }]

}
