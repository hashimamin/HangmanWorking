package com.notanexample.hangman;

import java.util.HashSet;

public class Game {
    int correctLetters;
    int attempt;
    String word;
    HashSet<Character> lettersGuessed;
    double startTime;

    public Game(String word) {
        this.word = word;
        this.correctLetters = 0;
        this.attempt = 0;
        this.lettersGuessed = new HashSet<Character>();
        this.startTime = System.currentTimeMillis();
    }
}
