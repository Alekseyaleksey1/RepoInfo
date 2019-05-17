package com.example.aleksei.repoinfo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.aleksei.repoinfo.ChiefPresenter;
import com.example.aleksei.repoinfo.Notifier;
import com.example.aleksei.repoinfo.RepositoriesActivity;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJOShort;

import java.util.ArrayList;
import java.util.List;

public class SQLiteWorker {
    static SQLiteWorker sqLiteWorker;
    static SQLiteTuner tuner;

    private SQLiteWorker() {
    }

    public static SQLiteWorker getInstance(Context appContext) {
        if (sqLiteWorker == null) {
            sqLiteWorker = new SQLiteWorker();
        }
        tuner = new SQLiteTuner(appContext, "db", null, 1);//todo put dbName in special class as static final
        return sqLiteWorker;
    }

    public void saveDataToDatabase(List<ModelPOJOShort> shortData) {//todo do this code in thread

        SQLiteDatabase db = tuner.getWritableDatabase();
        ContentValues contentValues;
        Log.i("saveDataToDatabase", "");
        for (int i = 0; i < shortData.size(); i++) {
            contentValues = new ContentValues();
            contentValues.put("id", shortData.get(i).getId());
            contentValues.put("name", shortData.get(i).getName());
            contentValues.put("fullName", shortData.get(i).getFull_name());
            contentValues.put("description", shortData.get(i).getDescription());
            contentValues.put("url", shortData.get(i).getUrl());
            contentValues.put("watcherscount", shortData.get(i).getWatchers_count());
            contentValues.put("forks", shortData.get(i).getForks());
            contentValues.put("stargazerscount", shortData.get(i).getStargazers_count());
            db.insert("dbTable", null, contentValues);
        }
        tuner.close();
        ChiefPresenter.setData(getDataFromDatabase());//todo model->presenter?
    }

    public ArrayList<ModelPOJOShort> getDataFromDatabase() {//todo do this code in thread

        ArrayList<ModelPOJOShort> arrayList = new ArrayList<>();
        SQLiteDatabase db = tuner.getWritableDatabase();
        Cursor cursor = db.query("dbTable", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int fullNameIndex = cursor.getColumnIndex("fullName");
            int descriptionIndex = cursor.getColumnIndex("description");
            int urlIndex = cursor.getColumnIndex("url");
            int watcherscountIndex = cursor.getColumnIndex("watcherscount");
            int forksIndex = cursor.getColumnIndex("forks");
            int stargazerscountIndex = cursor.getColumnIndex("stargazerscount");

            do {
                ModelPOJOShort pojoShort = new ModelPOJOShort();
                pojoShort.setId(cursor.getInt(idIndex));
                pojoShort.setName(cursor.getString(nameIndex));
                pojoShort.setFull_name(cursor.getString(fullNameIndex));
                pojoShort.setDescription(cursor.getString(descriptionIndex));
                pojoShort.setUrl(cursor.getString(urlIndex));
                pojoShort.setWatchers_count(cursor.getInt(watcherscountIndex));
                pojoShort.setForks(cursor.getInt(forksIndex));
                pojoShort.setStargazers_count(cursor.getInt(stargazerscountIndex));
                arrayList.add(pojoShort);
            } while (cursor.moveToNext());
        } else {
            Log.d("DBTable", "0 rows");
        }
        tuner.close();
        return arrayList;
    }
}
