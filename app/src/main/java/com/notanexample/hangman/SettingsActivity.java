package com.notanexample.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GestureDetectorCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    Switch nightModeSwitch;
    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        nightMode();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();
        gestureListener.setActivity(this);
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return true;
    }

    public void nightMode() {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        boolean isNightMode = settings.getBoolean("nightmode", false);

        if(isNightMode) {
            setTheme(R.style.DarkTheme);
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
