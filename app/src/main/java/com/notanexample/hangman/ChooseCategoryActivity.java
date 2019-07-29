package com.notanexample.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChooseCategoryActivity extends AppCompatActivity {
    ConstraintLayout parentLayout;
    Category[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        //set background color of activity
        parentLayout = findViewById(R.id.parentLayout);
        setBackgroundColor();

        // Setup category spinner
        Spinner categorySpinner = findViewById(R.id.categorySpinner);

        categories = DatabaseManager.getInstance(getApplicationContext()).getCategories();
        ArrayList<String> spinnerCategories = new ArrayList<String>();
        for (int i = 0; i < categories.length; i++) {
            spinnerCategories.add(categories[i].name);
        }

        ArrayAdapter<String> categoryDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerCategories);
        categoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryDataAdapter);

        // Setup difficulty spinner
        Spinner difficultySpinner = findViewById(R.id.difficultySpinner);
        ArrayList<String> spinnerDifficulties = new ArrayList<String>();
        spinnerDifficulties.add("Easy");
        spinnerDifficulties.add("Normal");
        spinnerDifficulties.add("Hard");
        ArrayAdapter<String> difficultyDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerDifficulties);
        difficultyDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyDataAdapter);

        // Start button action
        Button startGameButton = (Button) findViewById(R.id.startButton);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Spinner categorySpinner = findViewById(R.id.categorySpinner);
                Spinner difficultySpinner = findViewById(R.id.difficultySpinner);

                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("category", categories[categorySpinner.getSelectedItemPosition()].id);

                //Easy: 3, Normal: 4, Hard: 5
                intent.putExtra("difficulty", (difficultySpinner.getSelectedItemPosition() + 3));
                startActivity(intent);
                finish();
            }
        });



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
