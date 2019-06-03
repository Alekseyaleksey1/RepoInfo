package com.example.aleksei.repoinfo.model.database;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.aleksei.repoinfo.model.DataWorker;
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;

import java.util.ArrayList;

public class DataIntentService extends IntentService {

    public static final String ACTION_SAVE_DB = "saveDataToDatabase";
    public static final String ACTION_GET_DB = "getDataFromDatabase";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DataIntentService(String name) {
        super(name);
    }

    public DataIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent resultIntent = new Intent();
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case (ACTION_SAVE_DB): {
                    ArrayList<RepositoryModel> repositoriesList = intent.getParcelableArrayListExtra(DataWorker.REPOSITORIES_LIST_KEY);
                    for (RepositoryModel repository : repositoriesList) {
                        DataWorker.getInstance(this).getDb().getRepositoryDao().insert(repository);
                    }
                    resultIntent.setAction(ACTION_SAVE_DB);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
                    break;
                }
                case (ACTION_GET_DB): {
                    DataWorker.getInstance(this).setDataToRetrieve((ArrayList<RepositoryModel>) DataWorker.getInstance(this).getDb().getRepositoryDao().getAll());
                    resultIntent.setAction(ACTION_GET_DB);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
                    break;
                }
            }
        }
    }
}
