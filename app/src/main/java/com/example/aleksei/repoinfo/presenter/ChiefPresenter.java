package com.example.aleksei.repoinfo.presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aleksei.repoinfo.R;
import com.example.aleksei.repoinfo.model.DatabaseWorker;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;
import com.example.aleksei.repoinfo.view.RepositoriesFragment;
import com.example.aleksei.repoinfo.view.ViewActivity;

import java.io.File;


public class ChiefPresenter implements DatabaseWorker.DataPresentInDBCallback, DatabaseWorker.DataRetrievedFromDBCallback, DatabaseWorker.DataLoadedFromInternetCallback, RecyclerViewAdapter.ItemClickedInAdapterCallback {

    ViewActivity activityInstance;
    public IntentReceiver receiver;

    public boolean checkDBExists(Context appContext) {

        File dbFile = appContext.getDatabasePath("db");
        Log.i("checkDBExists", String.valueOf(dbFile.exists()));
        return dbFile.exists();
    }

    public boolean checkInternetAvailability(Context appContext) {

        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.i("checkInternet", String.valueOf(networkInfo != null && networkInfo.isConnected()));
        return networkInfo != null && networkInfo.isConnected();
    }

    public void onUIReady(Activity activity) {

        activityInstance = (ViewActivity) activity;//todo methods to attachActivityInstance/detachActivityInstance in Activity's onCreate/onDestroy
        Context appContext = this.activityInstance.getApplicationContext();
        DatabaseWorker.getInstance(appContext).registerForDataPresentCallback(this);
        DatabaseWorker.getInstance(appContext).registerForDataRetrievedCallback(this);
        DatabaseWorker.getInstance(appContext).registerForDataLoadedCallback(this);
        RepositoriesFragment.recyclerViewAdapter.registerForCallback(this);
//        lockScreenOrientation();
        if (checkDBExists(appContext)) {
            onDataInDBPresent();
        } else {
            if (checkInternetAvailability(appContext)) {
                DatabaseWorker.getInstance(appContext).getDataFromInternet();
            } else {
                Toast.makeText(appContext, "DB is non existing and Internet is not avaliable", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDataInDBPresent() {
        //activityInstance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        //lockScreenOrientation();
        DatabaseWorker.getInstance(activityInstance.getApplicationContext()).getDataFromDatabase(activityInstance.getApplicationContext());
        /* RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();*/
        //activityInstance.hideLoading();
    }

    @Override
    public void onDataFromInternetLoaded() {
        //activityInstance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        DatabaseWorker.getInstance(activityInstance.getApplicationContext()).saveDataToDatabase(activityInstance.getApplicationContext(), DatabaseWorker.getInstance(activityInstance.getApplicationContext()).arrayListShortResponce);
    }

    @Override
    public void onDataFromDBRetrieved() {
        RecyclerViewAdapter.setDataToAdapter(DatabaseWorker.dataToRetrieve);
        RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();
        activityInstance.hideLoading();
        //activityInstance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        //      unlockScreenOrientation();
    }

    @Override
    public void onItemClicked(View v) {
        RecyclerView recyclerView = activityInstance.findViewById(R.id.fragment_repositories_rv);
        int itemPosition = recyclerView.getChildAdapterPosition(v);
        activityInstance.detailedInfoFragment.setFullName(RecyclerViewAdapter.arrayList.get(itemPosition).getFullName());
    }

    /*private void lockScreenOrientation() {
        int currentOrientation = activityInstance.getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            activityInstance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            activityInstance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void unlockScreenOrientation() {
        activityInstance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }*/



    public void setReceiver(ViewActivity viewActivity) {
        receiver = new IntentReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("saveDataToDatabase");
        intentFilter.addAction("getDataFromDatabase");
        LocalBroadcastManager.getInstance(viewActivity.getApplicationContext()).registerReceiver(receiver, intentFilter);
    }

    public void removeReceiver(ViewActivity viewActivity){
        LocalBroadcastManager.getInstance(viewActivity.getApplicationContext()).unregisterReceiver(receiver);
    }
}

