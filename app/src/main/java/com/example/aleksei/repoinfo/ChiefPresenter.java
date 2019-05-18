package com.example.aleksei.repoinfo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aleksei.repoinfo.model.ChiefModel;
import com.example.aleksei.repoinfo.model.SQLiteWorker;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;

import java.io.File;


public class ChiefPresenter implements SQLiteWorker.DataPresentInDBCallback, RecyclerViewAdapter.ItemClickedInAdapterCallback {

    RepositoriesActivity activityInstance;
    //public static ArrayList data;

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

    public void onUIReady(RepositoriesActivity activityInstance) {

        this.activityInstance = activityInstance;//todo methods to attachActivityInstance/detachActivityInstance in Activity's onCreate/onDestroy
        Context appContext = this.activityInstance.getApplicationContext();
        SQLiteWorker.getInstance(appContext).registerForCallback(this);
        RepositoriesActivity.recyclerViewAdapter.registerForCallback(this);
        activityInstance.showLoadingWindow();

        if (checkDBExists(appContext)) {
            //informDataReady(appContext);
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

        //setData(SQLiteWorker.getInstance(activityInstance.getApplicationContext()).getDataFromDatabase());
        RecyclerViewAdapter.setDataToAdapter(SQLiteWorker.getInstance(activityInstance.getApplicationContext()).getDataFromDatabase());
        // RecyclerViewAdapter.arrayList = data;//
        RepositoriesActivity.recyclerViewAdapter.notifyDataSetChanged();
        activityInstance.hideLoadingWindow();

    }

    @Override
    public void onItemClicked(View v) {

        RecyclerView recyclerView = activityInstance.findViewById(R.id.rv_repositories);
        int itemPosition = recyclerView.getChildAdapterPosition(v);
        Intent intent = new Intent(activityInstance.getApplicationContext(), DetailedInfoActivity.class);
        intent.putExtra("modelPOJO", RecyclerViewAdapter.arrayList.get(itemPosition));
        activityInstance.startActivity(intent);

    }

    /*public void informDataReady(Context appContext) {
        setData(SQLiteWorker.getInstance(appContext).getDataFromDatabase());
    }*/

   /* public void setData(ArrayList data) {

        RecyclerViewAdapter.setDataToAdapter(data);
       // RecyclerViewAdapter.arrayList = data;//
        RepositoriesActivity.recyclerViewAdapter.notifyDataSetChanged();
        activityInstance.hideLoadingWindow();
    }*/

    /*public void onRecyclerViewItemClick(View clickedView) {

        //LayoutInflater inflater = (LayoutInflater) clickedView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = inflater.inflate(R.layout.activity_repositories, null);


        RecyclerView recyclerView = activityInstance.findViewById(R.id.rv_repositories);
        int itemPosition = recyclerView.getChildAdapterPosition(clickedView);
        Intent intent = new Intent(activityInstance.getApplicationContext(), DetailedInfoActivity.class);
        intent.putExtra("modelPOJO", RecyclerViewAdapter.arrayList.get(itemPosition));
        activityInstance.startActivity(intent);
    }*/


}
