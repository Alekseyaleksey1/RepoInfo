package com.example.aleksei.repoinfo.model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatabaseWorker {
    static private DatabaseWorker databaseWorker;
    static AppDatabase db;
    public ArrayList<RepositoryModel> arrayListShortResponce;
    public static DataCallback dataCallback;
    public static ArrayList<RepositoryModel> dataToRetrieve;

    private DatabaseWorker() {
    }

    public static DatabaseWorker getInstance(Context appContext) {
        if (databaseWorker == null) {
            databaseWorker = new DatabaseWorker();

        }
        db = Room.databaseBuilder(appContext, AppDatabase.class, "db").build();
        return databaseWorker;
    }

    public void registerForDataCallback(DataCallback callback) {
        dataCallback = callback;
    }

    public interface DataCallback {
        void onDataInDBPresent();
        void onDataFromDBRetrieved();
        void onDataFromInternetLoaded();
    }

    public void getDataFromInternet() {
        RetrofitTuner.getInstance().getJSONApi().getData().enqueue(new Callback<ArrayList<RepositoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RepositoryModel>> call, Response<ArrayList<RepositoryModel>> response) {
                arrayListShortResponce = response.body();
                dataCallback.onDataFromInternetLoaded();
            }

            @Override
            public void onFailure(Call<ArrayList<RepositoryModel>> call, Throwable t) {

            }
        });
    }

    public void saveDataToDatabase(Context appContext, ArrayList<RepositoryModel> repositoriesList) {
        Intent intent = new Intent(appContext, AsyncIntent.class);
        intent.putParcelableArrayListExtra("repositoriesList", repositoriesList);
        intent.setAction("saveDataToDatabase");
        appContext.startService(intent);
    }

    public void getDataFromDatabase(Context appContext) {
        Intent intent = new Intent(appContext, AsyncIntent.class);
        intent.setAction("getDataFromDatabase");
        appContext.startService(intent);
    }
}

