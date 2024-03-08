package com.example.timemanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PodomoroDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "podomoro.db";
    private static final int DATABASE_VERSION = 1;
    public PodomoroDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE podomoro (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time INTEGER, " +
                "breaks INTEGER, " +
                "shortBreak INTEGER, " +
                "longBreak INTEGER" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
