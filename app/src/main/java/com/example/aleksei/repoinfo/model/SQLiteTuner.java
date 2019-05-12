package com.example.aleksei.repoinfo.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteTuner extends SQLiteOpenHelper {
    public SQLiteTuner(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE dbTable (id INTEGER, name TEXT, fullName TEXT, description TEXT, url TEXT, subscriberscount INTEGER, forks INTEGER, stargazerscount INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
