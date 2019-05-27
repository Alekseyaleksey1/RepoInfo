package com.example.aleksei.repoinfo.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.aleksei.repoinfo.model.DatabaseWorker;

public class IntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case ("saveDataToDatabase"): {
                DatabaseWorker.dataPresentInDBCallback.onDataInDBPresent();
                break;
            }

            case ("getDataFromDatabase"): {
                DatabaseWorker.dataRetrievedFromDBCallback.onDataFromDBRetrieved();
                break;
            }
        }
    }
}
