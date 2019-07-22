package com.notanexample.hangman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    int correctLetters = 0;
    int attempt = 0;
    String word = "";
    String[] nouns = {"LIFE", "RING", "WOLF", "FISH", "TREE", "KING", "PIPE", "JEEP", "CATS", "LYNX",
            "DOGS", "MOON", "BALL", "CAKE", "FIRE", "WOOD", "ROSE", "ROCK", "LAND", "LADY"};
    String[] verbs = {"DRAW", "OPEN", "PEEK", "FALL", "WAIL", "SWIM", "WRAP", "SING", "FAKE", "FLOP",
            "GASP", "DASH", "KNIT", "JUMP", "KISS", "HANG", "JOIN", "GRIN", "GULP", "RAGE"};
    String[] adjectives = {"NEAR", "RICH", "POOR", "HARD", "SOFT", "TALL", "WILD", "MANY", "BEST", "SICK",
            "GOOD", "KIND", "HIGH", "DEEP", "PURE", "WISE", "DULL", "RUDE", "BUSY", "SOUR"};
    String[] words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String category = getIntent().getStringExtra("category");
        switch(category){
            case "nouns":
                words = nouns;
                break;
            case "verbs":
                words = verbs;
                break;
            case "adjectives":
                words = adjectives;
                break;
        }

        word = words[new Random().nextInt(words.length)];

        // Set word letters to underlined blanks
        String blankHTML = "<u>&nbsp;&nbsp;&nbsp;</u>";

        TextView firstCharacter = (TextView) findViewById(R.id.firstCharacter);
        firstCharacter.setText(Html.fromHtml(blankHTML));

        TextView secondCharacter = (TextView) findViewById(R.id.secondCharacter);
        secondCharacter.setText(Html.fromHtml(blankHTML));

        TextView thirdCharacter = (TextView) findViewById(R.id.thirdCharacter);
        thirdCharacter.setText(Html.fromHtml(blankHTML));

        TextView fourthCharacter = (TextView) findViewById(R.id.fourthCharacter);
        fourthCharacter.setText(Html.fromHtml(blankHTML));

    }


    public void letterClick(View v) {
        v.setClickable(false);

        Button button = (Button)v;
        Character letter = button.getText().charAt(0);

        if (Character.isLetter(letter)) {

            boolean correctAttempt = false;
            String letterHTML = "<u>&nbsp;"+letter+"&nbsp;</u>";

            // Check if letter is in mystery word
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == letter) {
                    correctAttempt = true;
                    correctLetters++;

                    // Reveal word letter
                    if (i == 0) {
                        TextView firstCharacter = (TextView) findViewById(R.id.firstCharacter);
                        firstCharacter.setText(Html.fromHtml(letterHTML));
                    } else if (i == 1) {
                        TextView secondCharacter = (TextView) findViewById(R.id.secondCharacter);
                        secondCharacter.setText(Html.fromHtml(letterHTML));
                    } else if (i == 2) {
                        TextView thirdCharacter = (TextView) findViewById(R.id.thirdCharacter);
                        thirdCharacter.setText(Html.fromHtml(letterHTML));
                    } else if (i == 3) {
                        TextView fourthCharacter = (TextView) findViewById(R.id.fourthCharacter);
                        fourthCharacter.setText(Html.fromHtml(letterHTML));
                    }
                }
            }


            if (correctAttempt) {
                v.setBackgroundColor(Color.parseColor("#2ecc71")); // Green

                if (correctLetters == word.length()) {
                    // Game Over: You Win!

                    // add +1 to wins
                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                    int wins = settings.getInt("wins", 0);
                    wins += 1;

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("wins", wins);
                    editor.commit();

                    new AlertDialog.Builder(GameActivity.this)
                            .setTitle("GAME OVER")
                            .setMessage("YOU WIN!")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();
                }

            } else {
                v.setBackgroundColor(Color.parseColor("#e74c3c")); // Red
                attempt++;

                // Update hangman graphic
                ImageView hangmanImageView = (ImageView) findViewById(R.id.hangmanImageView);
                if (attempt == 1) {
                    hangmanImageView.setImageResource(R.drawable.hangman_1);
                } else if (attempt == 2) {
                    hangmanImageView.setImageResource(R.drawable.hangman_2);
                } else if (attempt == 3) {
                    hangmanImageView.setImageResource(R.drawable.hangman_3);
                } else if (attempt == 4) {
                    hangmanImageView.setImageResource(R.drawable.hangman_4);
                } else if (attempt >= 5) {
                    hangmanImageView.setImageResource(R.drawable.hangman_5);

                    // Game Over: You lose!

                    // add +1 to loses
                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                    int loses = settings.getInt("loses", 0);
                    loses += 1;

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("loses", loses);
                    editor.commit();

                    new AlertDialog.Builder(GameActivity.this)
                            .setTitle("GAME OVER")
                            .setMessage("YOU LOSE!")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();

                }
            }
        }
    }


}
