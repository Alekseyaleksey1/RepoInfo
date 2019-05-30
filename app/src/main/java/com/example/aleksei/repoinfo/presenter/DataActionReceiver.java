package com.example.aleksei.repoinfo.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.aleksei.repoinfo.model.DataWorker;
import com.example.aleksei.repoinfo.model.database.DataIntentService;

public class DataActionReceiver extends BroadcastReceiver {

    private DataWorker dataWorker;

    DataActionReceiver(DataWorker dataWorker) {
        this.dataWorker = dataWorker;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case (DataIntentService.ACTION_SAVE_DB): {
                dataWorker.dataCallback.onDataInDBPresent();
                break;
            }
            case (DataIntentService.ACTION_GET_DB): {
                dataWorker.dataCallback.onDataFromDBRetrieved();
                break;
            }
        }
    }
}
