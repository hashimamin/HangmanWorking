package com.notanexample.hangman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GestureDetectorCompat;

import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    Game gameState;
    Timer timer;
    private static DecimalFormat timerFormat = new DecimalFormat("0.00");

    SensorManager sensorManager;
    Sensor proximitySensor;
    SensorEventListener proximitySensorListener;

    HangmanView hangmanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        nightMode();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        hangmanView = findViewById(R.id.hangmanView);

        // Setup game
        int category = getIntent().getIntExtra("categoryId", 1);
        int difficulty = getIntent().getIntExtra("difficulty", 0);
        String word = DatabaseManager.getInstance(getApplicationContext()).getRandomWord(category, difficulty);
        gameState = new Game(word);

        if (savedInstanceState != null) {
            gameState.correctLetters = savedInstanceState.getInt("correctLetters");
            gameState.attempt = savedInstanceState.getInt("attempt");
            gameState.word = savedInstanceState.getString("word");
            gameState.startTime = savedInstanceState.getDouble("startTime");

            String lettersGuessed = savedInstanceState.getString("lettersGuessed");

            for (int i = 0; i < lettersGuessed.length(); i++) {
                char letter = lettersGuessed.charAt(i);
                gameState.lettersGuessed.add(new Character(letter));

                Button button = findViewById(R.id.aButton);

                switch (letter) {
                    case 'A': button = findViewById(R.id.aButton); break;
                    case 'B': button = findViewById(R.id.bButton); break;
                    case 'C': button = findViewById(R.id.cButton); break;
                    case 'D': button = findViewById(R.id.dButton); break;
                    case 'E': button = findViewById(R.id.eButton); break;
                    case 'F': button = findViewById(R.id.fButton); break;
                    case 'G': button = findViewById(R.id.gButton); break;
                    case 'H': button = findViewById(R.id.hButton); break;
                    case 'I': button = findViewById(R.id.iButton); break;
                    case 'J': button = findViewById(R.id.jButton); break;
                    case 'K': button = findViewById(R.id.kButton); break;
                    case 'L': button = findViewById(R.id.lButton); break;
                    case 'M': button = findViewById(R.id.mButton); break;
                    case 'N': button = findViewById(R.id.nButton); break;
                    case 'O': button = findViewById(R.id.oButton); break;
                    case 'P': button = findViewById(R.id.pButton); break;
                    case 'Q': button = findViewById(R.id.qButton); break;
                    case 'R': button = findViewById(R.id.rButton); break;
                    case 'S': button = findViewById(R.id.sButton); break;
                    case 'T': button = findViewById(R.id.tButton); break;
                    case 'U': button = findViewById(R.id.uButton); break;
                    case 'V': button = findViewById(R.id.vButton); break;
                    case 'W': button = findViewById(R.id.wButton); break;
                    case 'X': button = findViewById(R.id.xButton); break;
                    case 'Y': button = findViewById(R.id.yButton); break;
                    case 'Z': button = findViewById(R.id.zButton); break;
                }

                boolean foundLetter = false;
                for (int j = 0; j < gameState.word.length(); j++) {
                    Character character = gameState.word.charAt(j);
                    if ((character == letter)) {
                        foundLetter = true;
                        break;
                    }
                }

                if (foundLetter) {
                    button.setBackgroundColor(Color.parseColor("#2ecc71"));
                } else {
                    button.setBackgroundColor(Color.parseColor("#e74c3c"));
                }
                button.setClickable(false);
            }

            hangmanView.attempts = gameState.attempt;
            hangmanView.invalidate();
        }

        // Set word letters to underlined blanks
        String blankHTML = "<u>&nbsp;&nbsp;&nbsp;</u>&nbsp;";

        String html = "";
        for (int i = 0; i < gameState.word.length(); i++) {
            char letter = gameState.word.charAt(i);
            if (gameState.lettersGuessed.contains(letter)) {
                html = html + "<u>&nbsp;"+letter+"&nbsp;</u>&nbsp;";
            } else {
                html = html + blankHTML;
            }
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
        }, 0, 75);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximitySensor != null) {
            proximitySensorListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    hangmanView.headSize = (2.0f*sensorEvent.values[0])/10.0f;
                    hangmanView.invalidate();

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {
                }
            };

            sensorManager.registerListener(proximitySensorListener,
                    proximitySensor, 2 * 1000 * 1000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_quit:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;

            case R.id.action_restart:
                restartActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
                hangmanView.setAttempts(gameState.attempt);

                    // Game Over: You lose!
                if(gameState.attempt >= 5) {

                    // add +1 to loses
                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                    int loses = settings.getInt("loses", 0);
                    loses += 1;

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("loses", loses);
                    editor.apply();

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

    public void nightMode() {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        boolean isNightMode = settings.getBoolean("nightmode", false);

        if(isNightMode) {
            setTheme(R.style.DarkTheme);
            TextView wordTextView = findViewById(R.id.wordText);
            wordTextView.setTextColor(Color.WHITE);

            TextView timerTextView = findViewById(R.id.timerText);
            timerTextView.setTextColor(Color.WHITE);
        }
        else
            setTheme(R.style.AppTheme);

    }

   
    //Restart
    public void restartActivity() {
        Restart.restartApp(this);
    }

    public void onDestroy() {
        timer.cancel();
        sensorManager.unregisterListener(proximitySensorListener);
        super.onDestroy();
    }

    public void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        b.putInt("correctLetters", gameState.correctLetters);
        b.putInt("attempt", gameState.attempt);
        b.putString("word", gameState.word);
        b.putDouble("startTime", gameState.startTime);
        b.putString("lettersGuessed", gameState.lettersGuessed.toString());
    }


}
