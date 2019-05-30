package com.example.aleksei.repoinfo.presenter;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.example.aleksei.repoinfo.model.DataWorker;
import com.example.aleksei.repoinfo.model.database.DataIntentService;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;
import com.example.aleksei.repoinfo.view.RepositoriesFragment;
import com.example.aleksei.repoinfo.view.ViewActivity;
import com.example.aleksei.repoinfo.view.ViewInterface;

import java.io.File;

public class ChiefPresenter implements DataWorker.DataCallback {

    private ViewInterface viewInterface;
    private Context appContext;
    private DataActionReceiver receiver;

    private boolean checkDBExists(Context appContext) {
        File dbFile = appContext.getDatabasePath("db");
        Log.i("checkDBExists", String.valueOf(dbFile.exists()));
        return dbFile.exists();
    }

    private boolean checkInternetAvailability(Context appContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.i("checkInternet", String.valueOf(networkInfo != null && networkInfo.isConnected()));
        return networkInfo != null && networkInfo.isConnected();
    }

    public void onUIReady(ViewInterface viewInterface, Context appContext) {
        viewInterface.showLoading();
        this.viewInterface =  viewInterface;
        this.appContext = appContext;
        DataWorker.getInstance(appContext).registerForDataCallback(this);
        if (checkDBExists(appContext)) {
            onDataInDBPresent();
        } else {
            if (checkInternetAvailability(appContext)) {
                DataWorker.getInstance(appContext).getDataFromInternet();
            } else {
                viewInterface.showInternetError(viewInterface);
            }
        }
    }

    @Override
    public void onDataInDBPresent() {
        DataWorker.getInstance(appContext).getDataFromDatabase(appContext);
    }

    @Override
    public void onDataFromInternetLoaded() {
        DataWorker.getInstance(appContext).saveDataToDatabase(appContext, DataWorker.getInstance(appContext).getListOfRepositoriesResponse());
    }

    @Override
    public void onDataFromDBRetrieved() {
        RecyclerViewAdapter.setDataToAdapter(DataWorker.getInstance(appContext).getDataToRetrieve());
        RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();
        viewInterface.hideLoading();
    }

    public void setReceiver(ViewActivity viewActivity) {
        receiver = new DataActionReceiver(DataWorker.getInstance(viewActivity.getApplicationContext()));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataIntentService.ACTION_SAVE_DB);
        intentFilter.addAction(DataIntentService.ACTION_GET_DB);
        LocalBroadcastManager.getInstance(viewActivity.getApplicationContext()).registerReceiver(receiver, intentFilter);
    }

    public void removeReceiver(ViewActivity viewActivity){
        LocalBroadcastManager.getInstance(viewActivity.getApplicationContext()).unregisterReceiver(receiver);
    }
}

