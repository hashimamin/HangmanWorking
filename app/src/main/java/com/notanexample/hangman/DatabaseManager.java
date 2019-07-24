package com.notanexample.hangman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseManager {

    private static DatabaseManager instance = null;

    private SQLiteDatabase db;
    private String backupWord = "CAT";

    private DatabaseManager(Context context) {
        try {
            copyDatabase(context);
            db = SQLiteDatabase.openDatabase(context.getDatabasePath("hangman.db").getPath(), null, SQLiteDatabase.OPEN_READWRITE);
        } catch (IOException e) {

        }
    }

    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }

        return instance;
    }

    private void copyDatabase(Context context) throws IOException {

        String databasePath = context.getDatabasePath("hangman.db").getPath();
        File dbfile = new File(databasePath);
        if (dbfile.exists()) {
            return;
        }

        InputStream in = context.getAssets().open("databases/hangman.db");
        OutputStream out = new FileOutputStream(databasePath);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer))>0) {
            out.write(buffer,0,length);
        }

        out.flush();
        out.close();
        in.close();
    }

    Category[] getCategories() {

        Cursor queryCursor = db.rawQuery("SELECT * FROM Category;", null);

        Category[] categories = new Category[queryCursor.getCount()];
        int i = 0;

        queryCursor.moveToFirst();
        while (!queryCursor.isAfterLast()) {
            categories[i] = new Category(
                    queryCursor.getInt(queryCursor.getColumnIndex("id")),
                    queryCursor.getString(queryCursor.getColumnIndex("name"))
            );
            i++;
            queryCursor.moveToNext();
        }
        queryCursor.close();

        return categories;
    }

    String getRandomWord(int categoryId, int length) {
        Cursor queryCursor = db.rawQuery("SELECT * FROM Words WHERE categoryId = "+categoryId+" AND length(word) = "+length+" ORDER BY RANDOM() LIMIT 1;", null);

        if (queryCursor.getCount() == 0) {
            return backupWord;
        }

        queryCursor.moveToFirst();

        String word = queryCursor.getString(queryCursor.getColumnIndex("word"));

        queryCursor.close();

        return word;
    }

    void storeHighScore(String name, double highscore) {
        ContentValues highscoreValues = new ContentValues();
        highscoreValues.put("name", name);
        highscoreValues.put("time", highscore);
        db.insert("Highscore", null, highscoreValues);
    }

    void deleteAllHighscores() {
        db.execSQL("DELETE FROM Highscore;");
    }

    ArrayList<Highscore> getHighscores() {
        Cursor queryCursor = db.rawQuery("SELECT * FROM Highscore;", null);

        if (queryCursor.getCount() == 0) {
            return new ArrayList<Highscore>();
        }

        ArrayList<Highscore> highscores = new ArrayList<Highscore>();

        queryCursor.moveToFirst();
        while (!queryCursor.isAfterLast()) {
            highscores.add(new Highscore(
                    queryCursor.getString(queryCursor.getColumnIndex("name")),
                    queryCursor.getDouble(queryCursor.getColumnIndex("time"))
            ));
            queryCursor.moveToNext();
        }
        queryCursor.close();

        return highscores;
    }
}
