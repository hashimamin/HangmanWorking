package com.notanexample.hangman;

import java.util.Comparator;

public class Highscore {

    int order;
    String name;
    double time;


    public Highscore(String name, double time) {
        this.order = -1;
        this.name = name;
        this.time = time;
    }
}
