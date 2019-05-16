package com.example.aleksei.repoinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aleksei.repoinfo.model.ChiefModel;
import com.example.aleksei.repoinfo.model.SQLiteWorker;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJOShort;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class RepositoriesActivity extends Activity {

    public RecyclerView rvRepositories;
    static public RecyclerViewAdapter recyclerViewAdapter;
    static ArrayList dataForShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);


        initializeUI();
        ChiefPresenter.onUIReady(getApplicationContext());
    }

    void initializeUI() {

        rvRepositories = findViewById(R.id.rv_repositories);
        rvRepositories.setLayoutManager(new LinearLayoutManager(this));

        dataForShowing = new ArrayList();
        //recyclerViewAdapter = new RecyclerViewAdapter(dataForShowing);
        recyclerViewAdapter = new RecyclerViewAdapter();
        rvRepositories.setAdapter(recyclerViewAdapter);


    }



}
