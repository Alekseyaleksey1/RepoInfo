package com.example.aleksei.repoinfo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.aleksei.repoinfo.model.ChiefModel;
import com.example.aleksei.repoinfo.model.SQLiteWorker;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJO;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJOShort;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;

import java.io.File;
import java.util.HashMap;

import static com.example.aleksei.repoinfo.RepositoriesActivity.rvRepositories;

public class ChiefPresenter {
    //static Context context;

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
        //context = appContext;

        if (checkDBExists(appContext)) {
            setupAdapter(appContext);
        } else {
            if (checkInternetAvailability(appContext)) {
                ChiefModel.getInstance().getDataFromInternet(appContext);
            } else {
                Toast.makeText(appContext, "DB is non existing and Internet is not avaliable", Toast.LENGTH_LONG).show();
            }

        }
        //checkDBExists(appContext);
        //checkInternetAvailability(appContext);
        //ChiefModel.getInstance().getDataFromInternet(appContext);
    }

    public static void setupAdapter(Context appContext) {
        rvRepositories.setAdapter(new RecyclerViewAdapter(SQLiteWorker.getInstance(appContext).getDataFromDatabase()));
    }

}
