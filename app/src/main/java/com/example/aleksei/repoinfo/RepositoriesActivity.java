package com.example.aleksei.repoinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.aleksei.repoinfo.model.ChiefModel;
import com.example.aleksei.repoinfo.model.SQLiteWorker;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJOShort;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/*Условия:
        	minApi 15
        	произвольный дизайн
        	использование фрагментов
        	все ориентации экрана
        	любая база данных или обертка для базы данных
        	без доступа к интернет соединению, показывать только данные из базы
        	поддержка всех плотностей экрана

        Цель:
        Создать приложение, которое делает http запросы на официальный GitHub API, сохраняет данные в базу данных, и показывает в интерфейсе.
        Документация - https://developer.github.com/v3/repos/
        Что загружать - список репозиториев https://github.com/square

        Как показывать:
        Первый экран - список репозиториев. В каждой ячейке списка - название репозитория, количество звезд, форков и watch-ей.
        По клику на репозиторий переход на второй экран - детали репозитория. Полное название, описание, url, количественные показатели, количество коммитов + (еще что нибудь по желанию).*/


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
        recyclerViewAdapter = new RecyclerViewAdapter();
        rvRepositories.setAdapter(recyclerViewAdapter);


    }

    static void showLoadingWindow() {

    }

    static void hideLoadingWindow() {

    }


}
