package com.example.aleksei.repoinfo.model;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.example.aleksei.repoinfo.model.api.RetrofitManager;
import com.example.aleksei.repoinfo.model.database.ApplicationDatabase;
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class DataWorker {

    public static final String INTERNET_DATA_ERROR_CASE = "internetDataError";
    public static final String DB_NAME = "db";
    private static DataWorker dataWorker;
    private ApplicationDatabase db;

    private DataWorker() {
    }

    public static synchronized DataWorker getInstance(Context appContext) {
        if (dataWorker == null) {
            dataWorker = new DataWorker();
            dataWorker.db = Room.databaseBuilder(appContext, ApplicationDatabase.class, DB_NAME).build();
        }
        return dataWorker;
    }

    private ApplicationDatabase getDb() {
        return db;
    }

    public Single<List<RepositoryModel>> getDataFromInternet() {
        return RetrofitManager.getInstance().getJSONApi().getData()
                .subscribeOn(Schedulers.io());
    }

    public Completable saveDataToDatabase(final List<RepositoryModel> repositoryModelList) {
        return Completable
                .create(new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(CompletableEmitter emitter) {
                        getDb().getRepositoryDao().insert(repositoryModelList);
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public Single<List<RepositoryModel>> getDataFromDatabase() {
        return getDb().getRepositoryDao().getAll()
                .subscribeOn(Schedulers.io());
    }
}

