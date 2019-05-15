package com.example.aleksei.repoinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aleksei.repoinfo.model.ChiefModel;
import com.example.aleksei.repoinfo.model.SQLiteWorker;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class RepositoriesActivity extends Activity {

    public static RecyclerView rvRepositories;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        context = getApplicationContext();

        ChiefModel.getDataFromInternet(this);
        rvRepositories = findViewById(R.id.rv_repositories);
        rvRepositories.setLayoutManager(new LinearLayoutManager(this));
    }

    public static void init() {
        rvRepositories.setAdapter(new RecyclerViewAdapter(SQLiteWorker.getDataFromDatabase(context)));
    }
}
