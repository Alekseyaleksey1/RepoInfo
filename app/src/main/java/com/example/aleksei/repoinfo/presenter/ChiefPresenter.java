package com.example.aleksei.repoinfo.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.aleksei.repoinfo.R;
import com.example.aleksei.repoinfo.model.DatabaseWorker;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;
import com.example.aleksei.repoinfo.view.RepositoriesFragment;
import com.example.aleksei.repoinfo.view.ViewActivity;

import java.io.File;


public class ChiefPresenter implements DatabaseWorker.DataCallback, RecyclerViewAdapter.ItemClickedCallback {

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
        DatabaseWorker.getInstance(appContext).registerForDataCallback(this);
        RepositoriesFragment.recyclerViewAdapter.registerForCallback(this);
        if (checkDBExists(appContext)) {
            onDataInDBPresent();
        } else {
            if (checkInternetAvailability(appContext)) {
                DatabaseWorker.getInstance(appContext).getDataFromInternet();
            } else {
                activityInstance.showInternetError(activityInstance);
            }
        }
    }

    @Override
    public void onDataInDBPresent() {
        DatabaseWorker.getInstance(activityInstance.getApplicationContext()).getDataFromDatabase(activityInstance.getApplicationContext());
    }

    @Override
    public void onDataFromInternetLoaded() {
        DatabaseWorker.getInstance(activityInstance.getApplicationContext()).saveDataToDatabase(activityInstance.getApplicationContext(), DatabaseWorker.getInstance(activityInstance.getApplicationContext()).arrayListShortResponce);
    }

    @Override
    public void onDataFromDBRetrieved() {
        RecyclerViewAdapter.setDataToAdapter(DatabaseWorker.dataToRetrieve);
        RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();
        activityInstance.hideLoading();
    }

    @Override
    public void onItemClicked(View v) {
        RecyclerView recyclerView = activityInstance.findViewById(R.id.fragment_repositories_rv);
        int itemPosition = recyclerView.getChildAdapterPosition(v);
        activityInstance.detailedInfoFragment.setDetailedData(RecyclerViewAdapter.arrayList.get(itemPosition));
    }

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

