package com.example.aleksei.repoinfo.model;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import com.example.aleksei.repoinfo.presenter.ChiefPresenter;

import java.util.ArrayList;

public class AsyncIntent extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AsyncIntent(String name) {
        super(name);
    }

    public AsyncIntent() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("AsyncIntent", "onHandleIntent");
        switch (intent.getAction()) {
            case ("saveDataToDatabase"): {
                ArrayList<RepositoryModel> repositoriesList = intent.getParcelableArrayListExtra("repositoriesList");

                for (RepositoryModel repository : repositoriesList) {
                    DatabaseWorker.db.getRepositoryDao().insert(repository);
                }
                Intent i = new Intent();
                i.setAction("saveDataToDatabase");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);

                //DatabaseWorker.dataPresentInDBCallback.onDataInDBPresent();
                break;
            }
            case ("getDataFromDatabase"): {

                DatabaseWorker.dataToRetrieve = (ArrayList<RepositoryModel>) DatabaseWorker.db.getRepositoryDao().getAll();
                //DatabaseWorker.dataRetrievedFromDBCallback.onDataFromDBRetrieved();
                Intent i = new Intent();
                i.setAction("getDataFromDatabase");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
                break;
            }
        }
    }
}
