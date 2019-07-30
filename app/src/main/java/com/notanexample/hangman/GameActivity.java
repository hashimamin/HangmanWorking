package com.notanexample.hangman;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    CoordinatorLayout parentLayout;
    Game gameState;
    Timer timer;
    private static DecimalFormat timerFormat = new DecimalFormat("#.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //set background color of activity
        parentLayout = findViewById(R.id.parentLayout);
        setBackgroundColor();

        // Setup game
        int category = getIntent().getIntExtra("categoryId", 1);
        int difficulty = getIntent().getIntExtra("difficulty", 3);
        String word = DatabaseManager.getInstance(getApplicationContext()).getRandomWord(category, difficulty);
        gameState = new Game(word);


        // Set word letters to underlined blanks
        String blankHTML = "<u>&nbsp;&nbsp;&nbsp;</u>&nbsp;";

        String html = "";
        for (int i = 0; i < gameState.word.length(); i++) {
            html = html + blankHTML;
        }

        TextView wordTextView = findViewById(R.id.wordText);
        wordTextView.setText(Html.fromHtml(html));

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final double seconds = ((System.currentTimeMillis() - gameState.startTime) / 1000) % 60;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView timeTextView = findViewById(R.id.timerText);
                        timeTextView.setText(timerFormat.format(seconds)+"s");
                    }
                });
            }
        }, 0, 100);


    }


    public void letterClick(View v) {
        v.setClickable(false);

        Button button = (Button)v;
        Character letter = button.getText().charAt(0);

        if (Character.isLetter(letter)) {
            gameState.lettersGuessed.add(letter);

            boolean correctAttempt = false;
            String html = "";

            // Check if letter is in mystery word
            for (int i = 0; i < gameState.word.length(); i++) {
                Character character = gameState.word.charAt(i);
                if ((character == letter)) {
                    correctAttempt = true;
                    gameState.correctLetters++;

                    html = html + "<u>&nbsp;"+letter+"&nbsp;</u>&nbsp;";

                } else if (gameState.lettersGuessed.contains(character)) {
                    html = html +  "<u>&nbsp;"+character+"&nbsp;</u>&nbsp;";

                } else {
                    html = html + "<u>&nbsp;&nbsp;&nbsp;</u>&nbsp;";
                }
            }

            TextView wordTextView = findViewById(R.id.wordText);
            wordTextView.setText(Html.fromHtml(html));


            if (correctAttempt) {
                v.setBackgroundColor(Color.parseColor("#2ecc71")); // Green

                if (gameState.correctLetters == gameState.word.length()) {
                    // Game Over: You Win!

                    // add +1 to wins
                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                    int wins = settings.getInt("wins", 0);
                    wins += 1;

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("wins", wins);
                    editor.apply();

                    timer.cancel();
                    final double seconds = ((System.currentTimeMillis() - gameState.startTime) / 1000) % 60;


                    final EditText input = new EditText(this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);


                    new AlertDialog.Builder(GameActivity.this)
                            .setTitle("GAME OVER")
                            .setMessage("YOU WIN!\nEnter name to store score:")
                            .setView(input)
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String name = input.getText().toString();
                                    if (name.length() > 0) {
                                        DatabaseManager.getInstance(getApplicationContext()).storeHighScore(name, seconds);
                                    }

                                    finish();
                                }
                            }).show();
                }

            } else {
                v.setBackgroundColor(Color.parseColor("#e74c3c")); // Red
                gameState.attempt++;

                // Update hangman graphic
                ImageView hangmanImageView = (ImageView) findViewById(R.id.hangmanImageView);
                if (gameState.attempt == 1) {
                    hangmanImageView.setImageResource(R.drawable.hangman_1);
                } else if (gameState.attempt == 2) {
                    hangmanImageView.setImageResource(R.drawable.hangman_2);
                } else if (gameState.attempt == 3) {
                    hangmanImageView.setImageResource(R.drawable.hangman_3);
                } else if (gameState.attempt == 4) {
                    hangmanImageView.setImageResource(R.drawable.hangman_4);
                } else if (gameState.attempt >= 5) {
                    hangmanImageView.setImageResource(R.drawable.hangman_5);

                    // Game Over: You lose!

                    // add +1 to loses
                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                    int loses = settings.getInt("loses", 0);
                    loses += 1;

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("loses", loses);
                    editor.commit();

                    timer.cancel();

                    new AlertDialog.Builder(GameActivity.this)
                            .setTitle("GAME OVER")
                            .setMessage("YOU LOSE!\nThe word was: \"" + gameState.word + "\"")
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
   
    //Restart
    public void restartActivity(View view) {
        Restart.restartApp(this);
    }
}
