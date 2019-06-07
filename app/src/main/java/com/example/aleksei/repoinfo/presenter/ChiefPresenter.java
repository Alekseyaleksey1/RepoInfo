package com.example.aleksei.repoinfo.presenter;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import com.example.aleksei.repoinfo.model.DataWorker;
import com.example.aleksei.repoinfo.model.database.DataIntentService;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;
import com.example.aleksei.repoinfo.view.RepositoriesFragment;
import com.example.aleksei.repoinfo.view.ViewActivity;
import com.example.aleksei.repoinfo.view.ViewInterface;
import java.io.File;

public class ChiefPresenter implements DataWorker.DataCallback {

    public static final String INTERNET_AVAILABILITY_ERROR_CASE = "internetAvailabilityError";
    public static final String INTERNET_DATA_ERROR_CASE = "internetDataError";
    private ViewInterface viewInterfaceInstance;
    private DataWorker dataWorkerInstance;
    private Context appContext;
    private DataActionReceiver receiver;

    public ChiefPresenter(DataWorker dataWorkerInstance, ViewInterface viewInterface, Context appContext) {
        this.dataWorkerInstance = dataWorkerInstance;
        this.viewInterfaceInstance = viewInterface;
        this.appContext = appContext;
    }

    private boolean checkDBExists(Context appContext) {
        File dbFile = appContext.getDatabasePath(DataWorker.DB_NAME);
        return dbFile.exists();
    }

    private boolean checkInternetAvailability(Context appContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void onUIReady() {
        viewInterfaceInstance.showLoading();
        dataWorkerInstance.registerForDataCallback(this);
        if (checkDBExists(appContext)) {
            onDataInDBPresent();
        } else {
            if (checkInternetAvailability(appContext)) {
                dataWorkerInstance.getDataFromInternet();
            } else {
                viewInterfaceInstance.showError(INTERNET_AVAILABILITY_ERROR_CASE);
            }
        }
    }

    @Override
    public void onDataInDBPresent() {
        dataWorkerInstance.getDataFromDatabase(appContext);
    }

    @Override
    public void onDataFromInternetLoaded() {
        dataWorkerInstance.saveDataToDatabase(appContext, dataWorkerInstance.getListOfRepositoriesResponse());
    }

    @Override
    public void onDataFromDBRetrieved() {
        RecyclerViewAdapter.setDataToAdapter(dataWorkerInstance.getDataToRetrieve());
        RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();
        viewInterfaceInstance.hideLoading();
    }

    @Override
    public void onDataError() {
        viewInterfaceInstance.showError(INTERNET_DATA_ERROR_CASE);
    }

    public void setReceiver() {
        receiver = new DataActionReceiver(dataWorkerInstance);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataIntentService.ACTION_SAVE_DB);
        intentFilter.addAction(DataIntentService.ACTION_GET_DB);
        LocalBroadcastManager.getInstance(appContext).registerReceiver(receiver, intentFilter);
    }

    public void removeReceiver() {
        LocalBroadcastManager.getInstance(appContext).unregisterReceiver(receiver);
    }

    public void prepareClickedView(View clickedView) {
        viewInterfaceInstance.showItemOnClickedPosition(clickedView);
    }
}

