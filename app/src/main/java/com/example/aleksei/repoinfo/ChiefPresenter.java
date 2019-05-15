package com.example.aleksei.repoinfo;

import android.content.Context;
import android.content.Intent;

import com.example.aleksei.repoinfo.model.ChiefModel;
import com.example.aleksei.repoinfo.model.SQLiteWorker;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJO;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJOShort;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;

import java.util.HashMap;

import static com.example.aleksei.repoinfo.RepositoriesActivity.rvRepositories;

public class ChiefPresenter {
   static Context context;

    public static boolean checkDBExists(){

        return false;
    }

    public static boolean checkInternetAvailability(){

        return false;
    }





    public static void onUIReady(Context appContext){
        context = appContext;

        ChiefModel.getInstance().getDataFromInternet(context);
    }

    public static void setupAdapter() {
        rvRepositories.setAdapter(new RecyclerViewAdapter(SQLiteWorker.getInstance(context).getDataFromDatabase()));
    }

}
