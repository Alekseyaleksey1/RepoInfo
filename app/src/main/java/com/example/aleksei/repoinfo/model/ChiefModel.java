package com.example.aleksei.repoinfo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.aleksei.repoinfo.model.pojo.ModelPOJODetailed;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJOShort;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiefModel {

    static ArrayList<ModelPOJOShort> arrayListShortResponce;
    static ArrayList<ModelPOJODetailed> arrayListDetailedResponce;

//todo is DB existing? false - getDataFromInterner-saveDataFromInternet, true - getDataFromDatabase

    public static void getDataFromInternet(final Context context) {
        RetrofitTuner.getInstance().getJSONApi().getData().enqueue(new Callback<ArrayList<ModelPOJOShort>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelPOJOShort>> call, Response<ArrayList<ModelPOJOShort>> response) {

                arrayListShortResponce = response.body();
                arrayListDetailedResponce = new ArrayList<>();

                for (int i = 0; i < arrayListShortResponce.size(); i++) {

                    RetrofitTuner.getInstance().getJSONApi().getDetailedData(arrayListShortResponce.get(i).getFull_name()).enqueue(new Callback<ModelPOJODetailed>() {//todo adequate naming Full_name
                        @Override
                        public void onResponse(Call<ModelPOJODetailed> call, Response<ModelPOJODetailed> detailedResponse) {

                            arrayListDetailedResponce.add(detailedResponse.body());

                        }

                        @Override
                        public void onFailure(Call<ModelPOJODetailed> call, Throwable t) {
                            //todo add downloading error message
                        }
                    });
                }

                saveDataFromInternet(context, arrayListShortResponce, arrayListDetailedResponce);
            }

            @Override
            public void onFailure(Call<ArrayList<ModelPOJOShort>> call, Throwable t) {
                //todo add downloading error message
            }
        });
    }

    private static void saveDataFromInternet(Context context, ArrayList<ModelPOJOShort> shortData, ArrayList<ModelPOJODetailed> detailedData) {
        //todo do this code in thread
        //todo move this code to special class SQLiteWorker
        SQLiteTuner tuner = new SQLiteTuner(context, "db", null, 1);//todo put dbName in special class as static final
        SQLiteDatabase db = tuner.getWritableDatabase();
        ContentValues contentValues;

        for (int i = 0; i < shortData.size(); i++) {
            contentValues = new ContentValues();
            contentValues.put("id", shortData.get(i).getId());
            contentValues.put("name", shortData.get(i).getName());
            contentValues.put("fullName", shortData.get(i).getFull_name());
            contentValues.put("description", shortData.get(i).getDescription());
            contentValues.put("url", shortData.get(i).getUrl());
            contentValues.put("subscriberscount", detailedData.get(i).getSubscribers_count());
            contentValues.put("forks", detailedData.get(i).getForks());
            contentValues.put("stargazerscount", detailedData.get(i).getStargazers_count());
            db.insert("dbTable", null, contentValues);
        }
        tuner.close();

    }

    public static ArrayList<HashMap> getDataFromDatabase(Context context) {

        ArrayList<HashMap> mapArrayList = new ArrayList<>();
        HashMap<String, String> hashMap;

        SQLiteTuner tuner = new SQLiteTuner(context, "db", null, 1); //todo put dbName in special class as static final
        SQLiteDatabase db = tuner.getWritableDatabase();
        Cursor cursor = db.query("dbTable", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int fullNameIndex = cursor.getColumnIndex("fullName");
            int descriptionIndex = cursor.getColumnIndex("description");
            int urlIndex = cursor.getColumnIndex("url");
            int subscriberscountIndex = cursor.getColumnIndex("subscriberscount");
            int forksIndex = cursor.getColumnIndex("forks");
            int stargazerscountIndex = cursor.getColumnIndex("stargazerscount");

            do {
                hashMap = new HashMap<>();
                hashMap.put("id", String.valueOf(cursor.getInt(idIndex)));
                hashMap.put("name", cursor.getString(nameIndex));
                hashMap.put("fullName", cursor.getString(fullNameIndex));
                hashMap.put("description", cursor.getString(descriptionIndex));
                hashMap.put("url", cursor.getString(urlIndex));
                hashMap.put("subscriberscount", String.valueOf(cursor.getInt(subscriberscountIndex)));
                hashMap.put("forks", String.valueOf(cursor.getInt(forksIndex)));
                hashMap.put("stargazerscount", String.valueOf(cursor.getInt(stargazerscountIndex)));
                mapArrayList.add(hashMap);
            } while (cursor.moveToNext());
        } else {
            Log.d("DBTable", "0 rows");
        }
        tuner.close();

        return mapArrayList;
    }
}
