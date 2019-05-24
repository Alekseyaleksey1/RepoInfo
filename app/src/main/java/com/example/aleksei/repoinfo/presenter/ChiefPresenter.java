package com.example.aleksei.repoinfo.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aleksei.repoinfo.R;
import com.example.aleksei.repoinfo.model.ChiefModel;
import com.example.aleksei.repoinfo.model.SQLiteWorker;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;
import com.example.aleksei.repoinfo.view.RepositoriesFragment;
import com.example.aleksei.repoinfo.view.ViewActivity;

import java.io.File;


public class ChiefPresenter implements SQLiteWorker.DataPresentInDBCallback, RecyclerViewAdapter.ItemClickedInAdapterCallback {

    ViewActivity activityInstance;

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
        SQLiteWorker.getInstance(appContext).registerForCallback(this);
        RepositoriesFragment.recyclerViewAdapter.registerForCallback(this);

        if (checkDBExists(appContext)) {
            onDataInDBPresent();
        } else {
            if (checkInternetAvailability(appContext)) {
                ChiefModel.getInstance().getDataFromInternet(appContext);
            } else {
                Toast.makeText(appContext, "DB is non existing and Internet is not avaliable", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDataInDBPresent() {

        RecyclerViewAdapter.setDataToAdapter(SQLiteWorker.getInstance(activityInstance.getApplicationContext()).getDataFromDatabase());
        RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();
        activityInstance.hideLoading();
    }

    @Override
    public void onItemClicked(View v) {

        RecyclerView recyclerView = activityInstance.findViewById(R.id.fragment_repositories_rv);
        int itemPosition = recyclerView.getChildAdapterPosition(v);
        activityInstance.detailedInfoFragment.setFullName(RecyclerViewAdapter.arrayList.get(itemPosition).getFullName());
    }
}
