package com.example.aleksei.repoinfo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;

public class RepositoriesActivity extends Activity {

    RecyclerView rvRepositories;
    ArrayList<HashMap> mapArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        //test
        mapArrayList = new ArrayList<>();
        HashMap<String, String> hashMap;

        for (int i = 0; i < 20; i++) {
            hashMap = new HashMap<>();
            hashMap.put("nameKey", "name");
            hashMap.put("starsKey", "stars");
            hashMap.put("forksKey", "forks");
            hashMap.put("watchesKey", "watches");
            mapArrayList.add(hashMap);
        }
        //testend

        rvRepositories = findViewById(R.id.rv_repositories);
        rvRepositories.setLayoutManager(new LinearLayoutManager(this));
        rvRepositories.setAdapter(new RecyclerViewAdapter(this, mapArrayList));

    }
}
