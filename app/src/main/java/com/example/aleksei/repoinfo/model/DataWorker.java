package com.example.aleksei.repoinfo.model;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.example.aleksei.repoinfo.model.api.RetrofitManager;
import com.example.aleksei.repoinfo.model.database.ApplicationDatabase;
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import java.util.ArrayList;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DataWorker {

    public static final String INTERNET_DATA_ERROR_CASE = "internetDataError";
    public static final String DB_NAME = "db";
    private static DataWorker dataWorker;
    private ArrayList<RepositoryModel> listOfRepositoriesResponse;
    private ArrayList<RepositoryModel> dataToRetrieve;
    private ApplicationDatabase db;

    private DataWorker() {
    }

    public static DataWorker getInstance(Context appContext) {
        if (dataWorker == null) {
            dataWorker = new DataWorker();
            dataWorker.db = Room.databaseBuilder(appContext, ApplicationDatabase.class, DB_NAME).build();
        }
        return dataWorker;
    }

    private ArrayList<RepositoryModel> getListOfRepositoriesResponse() {
        return listOfRepositoriesResponse;
    }

    public void setListOfRepositoriesResponse(ArrayList<RepositoryModel> listOfRepositoriesResponse) {
        this.listOfRepositoriesResponse = listOfRepositoriesResponse;
    }

    private ApplicationDatabase getDb() {
        return db;
    }

    private void setDataToRetrieve(ArrayList<RepositoryModel> dataToRetrieve) {
        this.dataToRetrieve = dataToRetrieve;
    }

    public ArrayList<RepositoryModel> getDataToRetrieve() {
        return dataToRetrieve;
    }

    public Observable<ArrayList<RepositoryModel>> getDataFromInternet() {
        return RetrofitManager.getInstance().getJSONApi().getData()
                .subscribeOn(Schedulers.io());
    }

    public Completable saveDataToDatabase() {
        return Completable
                .create(new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(CompletableEmitter emitter) {
                        for (RepositoryModel repository : getListOfRepositoriesResponse()) {
                            getDb().getRepositoryDao().insert(repository);
                        }
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public Completable getDataFromDatabase() {
        return Completable
                .create(new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(CompletableEmitter emitter) {
                        setDataToRetrieve((ArrayList<RepositoryModel>) getDb().getRepositoryDao().getAll());
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io());
    }
}

