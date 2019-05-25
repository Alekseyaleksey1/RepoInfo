package com.example.aleksei.repoinfo.model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatabaseWorker {
    static private DatabaseWorker databaseWorker;
    static private AppDatabase db;
    public List<RepositoryModel> arrayListShortResponce;
    static private DataPresentInDBCallback dataPresentInDBCallback;
    static private DataRetrievedFromDBCallback dataRetrievedFromDBCallback;
    static private DataLoadedFromInternetCallback dataLoadedFromInternetCallback;
    public static ArrayList<RepositoryModel> dataToRetrieve;


    private DatabaseWorker() {
    }

    public static DatabaseWorker getInstance(Context appContext) {
        if (databaseWorker == null) {
            databaseWorker = new DatabaseWorker();
        }
        db = Room.databaseBuilder(appContext, AppDatabase.class, "db").allowMainThreadQueries().build();
        return databaseWorker;
    }

    public void registerForDataPresentCallback(DataPresentInDBCallback callback) {
        dataPresentInDBCallback = callback;
    }

    public void registerForDataRetrievedCallback(DataRetrievedFromDBCallback callback) {
        dataRetrievedFromDBCallback = callback;
    }

    public void registerForDataLoadedCallback(DataLoadedFromInternetCallback callback) {
        dataLoadedFromInternetCallback = callback;
    }

    public interface DataPresentInDBCallback {
        void onDataInDBPresent();
    }

    public interface DataRetrievedFromDBCallback {
        void onDataFromDBRetrieved();
    }

    public interface DataLoadedFromInternetCallback {
        void onDataFromInternetLoaded();
    }


    public void getDataFromInternet() {

        RetrofitTuner.getInstance().getJSONApi().getData().enqueue(new Callback<ArrayList<RepositoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RepositoryModel>> call, Response<ArrayList<RepositoryModel>> response) {
                arrayListShortResponce = response.body();
                dataLoadedFromInternetCallback.onDataFromInternetLoaded();
                //saveDataToDatabase(arrayListShortResponce);//todo callback
            }

            @Override
            public void onFailure(Call<ArrayList<RepositoryModel>> call, Throwable t) {

            }
        });
    }

    public void saveDataToDatabase(List<RepositoryModel> repositoriesList) {

        AsyncSaver saver = new AsyncSaver();
        saver.execute(repositoriesList);
        /*for (RepositoryModel repository : repositoriesList) {
            db.getRepositoryDao().insert(repository);
        }*/



        /*SQLiteDatabase db = tuner.getWritableDatabase();
        ContentValues contentValues;
        Log.i("saveDataToDatabase", "");
        for (int i = 0; i < shortData.size(); i++) {
            contentValues = new ContentValues();
            contentValues.put("id", shortData.get(i).getId());
            contentValues.put("name", shortData.get(i).getName());
            contentValues.put("fullName", shortData.get(i).getFullName());
            contentValues.put("description", shortData.get(i).getDescription());
            contentValues.put("url", shortData.get(i).getUrl());
            contentValues.put("watcherscount", shortData.get(i).getWatchersCount());
            contentValues.put("forks", shortData.get(i).getForks());
            contentValues.put("stargazerscount", shortData.get(i).getStargazersCount());
            db.insert("dbTable", null, contentValues);
        }
        tuner.close();*/
        //dataPresentInDBCallback.onDataInDBPresent();
    }

    public void getDataFromDatabase() {
        AsyncGetter asyncGetter = new AsyncGetter();
        asyncGetter.execute();

        //ArrayList<RepositoryModel> arrayList = (ArrayList<RepositoryModel>) db.getRepositoryDao().getAll();


        /*SQLiteDatabase db = tuner.getWritableDatabase();
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
                RepositoryModel pojoShort = new RepositoryModel();
                pojoShort.setId(cursor.getInt(idIndex));
                pojoShort.setName(cursor.getString(nameIndex));
                pojoShort.setFullName(cursor.getString(fullNameIndex));
                pojoShort.setDescription(cursor.getString(descriptionIndex));
                pojoShort.setUrl(cursor.getString(urlIndex));
                pojoShort.setWatchersCount(cursor.getInt(watcherscountIndex));
                pojoShort.setForks(cursor.getInt(forksIndex));
                pojoShort.setStargazersCount(cursor.getInt(stargazerscountIndex));
                arrayList.add(pojoShort);
            } while (cursor.moveToNext());
        } else {
            Log.d("DBTable", "0 rows");
        }
        tuner.close();*/
    }

    static class AsyncSaver extends AsyncTask<List<RepositoryModel>, Void, Void> {

        @Override
        protected Void doInBackground(List<RepositoryModel>... lists) {
            for (RepositoryModel repository : lists[0]) {
                db.getRepositoryDao().insert(repository);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dataPresentInDBCallback.onDataInDBPresent();
        }
    }

    static class AsyncGetter extends AsyncTask<Void, Void, ArrayList<RepositoryModel>> {

        @Override
        protected ArrayList<RepositoryModel> doInBackground(Void... voids) {
            return (ArrayList<RepositoryModel>) db.getRepositoryDao().getAll();
        }

        @Override
        protected void onPostExecute(ArrayList<RepositoryModel> repositories) {
            super.onPostExecute(repositories);
            dataToRetrieve = repositories;
            dataRetrievedFromDBCallback.onDataFromDBRetrieved();
        }
    }
}

