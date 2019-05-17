package com.example.aleksei.repoinfo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.aleksei.repoinfo.model.ChiefModel;
import com.example.aleksei.repoinfo.model.SQLiteWorker;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;


public class ChiefPresenter {

    public static boolean checkDBExists(Context appContext) {

        File dbFile = appContext.getDatabasePath("db");
        Log.i("checkDBExists", String.valueOf(dbFile.exists()));
        return dbFile.exists();
    }

    public static boolean checkInternetAvailability(Context appContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.i("checkInternet", String.valueOf(networkInfo != null && networkInfo.isConnected()));
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void onUIReady(Context appContext) {

        if (checkDBExists(appContext)) {
            informDataReady(appContext);
        } else {
            if (checkInternetAvailability(appContext)) {
                ChiefModel.getInstance().getDataFromInternet(appContext);

            } else {
                Toast.makeText(appContext, "DB is non existing and Internet is not avaliable", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void informDataReady(Context appContext) {
        setData(SQLiteWorker.getInstance(appContext).getDataFromDatabase());
    }

    public static void setData(ArrayList data) {

        RecyclerViewAdapter.arrayList = data;
        RepositoriesActivity.recyclerViewAdapter.notifyDataSetChanged();
    }

    public static void onRecyclerViewItemClick(View clickedView) {

        LayoutInflater inflater = (LayoutInflater) clickedView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_repositories, null);
        RecyclerView recyclerView = v.findViewById(R.id.rv_repositories);
        int itemPosition = recyclerView.getChildAdapterPosition(clickedView);
        Intent intent = new Intent(v.getContext(), DetailedInfoActivity.class);
        intent.putExtra("modelPOJO", RecyclerViewAdapter.arrayList.get(itemPosition));
        v.getContext().startActivity(intent);
    }
}
