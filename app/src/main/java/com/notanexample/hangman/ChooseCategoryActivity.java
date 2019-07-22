package com.notanexample.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        Button btnNouns = findViewById(R.id.btnNouns);
        Button btnVerbs = findViewById(R.id.btnVerbs);
        Button btnAdjectives = findViewById(R.id.btnAdjectives);

        btnNouns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("category", "nouns");
                startActivity(intent);
                finish();
            }
        });

        btnVerbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("category", "verbs");
                startActivity(intent);
                finish();
            }
        });

        btnAdjectives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("category", "adjectives");
                startActivity(intent);
                finish();
            }
        });
    }
}
