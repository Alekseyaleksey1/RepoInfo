package com.example.aleksei.repoinfo.model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;

import com.example.aleksei.repoinfo.model.api.RetrofitManager;
import com.example.aleksei.repoinfo.model.database.ApplicationDatabase;
import com.example.aleksei.repoinfo.model.database.DataIntentService;
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataWorker {
    static private DataWorker dataWorker;
    public static final String REPOSITORIES_LIST_KEY = "repositoriesList";
    private ArrayList<RepositoryModel> listOfRepositoriesResponse;
    public DataCallback dataCallback;
    private ArrayList<RepositoryModel> dataToRetrieve;
    private ApplicationDatabase db;

    private DataWorker() {
    }

    public static DataWorker getInstance(Context appContext) {
        if (dataWorker == null) {
            dataWorker = new DataWorker();
            dataWorker.db = Room.databaseBuilder(appContext, ApplicationDatabase.class, "db").build();
        }
        return dataWorker;
    }

    public ArrayList<RepositoryModel> getListOfRepositoriesResponse() {
        return listOfRepositoriesResponse;
    }

    private void setListOfRepositoriesResponse(ArrayList<RepositoryModel> listOfRepositoriesResponse) {
        this.listOfRepositoriesResponse = listOfRepositoriesResponse;
    }

    public ApplicationDatabase getDb() {
        return db;
    }

    public void setDataToRetrieve(ArrayList<RepositoryModel> dataToRetrieve) {
        this.dataToRetrieve = dataToRetrieve;
    }

    public ArrayList<RepositoryModel> getDataToRetrieve() {
        return dataToRetrieve;
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
        RetrofitManager.getInstance().getJSONApi().getData().enqueue(new Callback<ArrayList<RepositoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RepositoryModel>> call, Response<ArrayList<RepositoryModel>> response) {
                setListOfRepositoriesResponse(response.body());
                dataCallback.onDataFromInternetLoaded();
            }

            @Override
            public void onFailure(Call<ArrayList<RepositoryModel>> call, Throwable t) {

            }
        });
    }

    public void saveDataToDatabase(Context appContext, ArrayList<RepositoryModel> repositoriesList) {
        Intent intent = new Intent(appContext, DataIntentService.class);
        intent.putParcelableArrayListExtra(REPOSITORIES_LIST_KEY, repositoriesList);
        intent.setAction(DataIntentService.ACTION_SAVE_DB);
        appContext.startService(intent);
    }

    public void getDataFromDatabase(Context appContext) {
        Intent intent = new Intent(appContext, DataIntentService.class);
        intent.setAction(DataIntentService.ACTION_GET_DB);
        appContext.startService(intent);
    }
}

