package com.notanexample.hangman;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HighscoreActivity extends AppCompatActivity {
    ConstraintLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        //set background color of activity
        parentLayout = findViewById(R.id.parentLayout);
        setBackgroundColor();

        ArrayList<Highscore> highscores = DatabaseManager.getInstance(getApplicationContext()).getHighscores();

        // Sort highscores
        Collections.sort(highscores, new Comparator<Highscore>() {
            @Override
            public int compare(Highscore a, Highscore b) {
                if (a.time < b.time) return -1;
                if (a.time > b.time) return 1;
                return 0;
            }
        });

        // Set order
        for (int i = 0; i < highscores.size(); i++) {
            highscores.get(i).order = (i + 1);
        }

        RecyclerView recyclerView = findViewById(R.id.highscoreRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter adapter = new HighscoreAdapter(highscores);
        recyclerView.setAdapter(adapter);



    }

    private class HighscoreHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Highscore mHighscore;
        private TextView mNameTextView;
        private TextView mHighscoreTextView;

        public HighscoreHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_highscore, parent,false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.nameText);
            mHighscoreTextView = itemView.findViewById(R.id.highscoreText);
        }

        public void bind(Highscore highscore) {
            mHighscore = highscore;
            if (highscore.order > 0) {
                mNameTextView.setText(mHighscore.order + ". " + mHighscore.name);
            } else {
                mNameTextView.setText(mHighscore.name);
            }
            mHighscoreTextView.setText("Time: " + mHighscore.time + " sec");
        }
        @Override
        public void onClick(View view) {

        }

    }

    private class HighscoreAdapter extends RecyclerView.Adapter<HighscoreHolder> {
        private List<Highscore> mHighscores;
        public HighscoreAdapter(List<Highscore> bands){
            mHighscores = bands;
        }

        @Override
        public HighscoreHolder onCreateViewHolder(ViewGroup parent,int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new HighscoreHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(HighscoreHolder holder,int position) {
            Highscore highscore = mHighscores.get(position);
            holder.bind(highscore);
        }

        @Override
        public int getItemCount(){
            return mHighscores.size();
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