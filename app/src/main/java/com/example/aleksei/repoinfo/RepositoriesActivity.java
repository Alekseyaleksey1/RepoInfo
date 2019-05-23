package com.example.aleksei.repoinfo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;
import java.util.ArrayList;

/*Условия:  minApi 15
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

public class RepositoriesActivity extends FragmentActivity {

    static public RecyclerViewAdapter recyclerViewAdapter;
    //public RecyclerView rvRepositories;
    //ArrayList dataForShowing;
    static ChiefPresenter chiefPresenter;
    ProgressBar progressBar;
    RepositoriesFragment repositoriesFragment;
    DetailedInfoFragment detailedInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        initializeUI();


        //chiefPresenter.onUIReady(this);
    }



    void initializeUI() {


        progressBar = findViewById(R.id.activity_repositories_pb);
        showLoading();



        //rvRepositories = findViewById(R.id.fragment_repositories_rv);
        //repositoriesFragment.repoFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //dataForShowing = new ArrayList();
        //recyclerViewAdapter = new RecyclerViewAdapter();
        //repositoriesFragment.repoFragmentRecyclerView.setAdapter(recyclerViewAdapter);
        chiefPresenter = new ChiefPresenter();
        setupFragments();

        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.*/

        //ConstraintLayout. В нем LinearLayout и ProgressBar. В LinearLayout два FrameLayout. В них фрагменты ListFragment, DetailedInfoFragment.
        //Запускается апп, пошла загрузка, показываю ProgressBar, скрываю фрагменты. Загрузились данные - скрываю ProgressBar, показываю фрагменты
    }


    void setupFragments(){
        repositoriesFragment = new RepositoriesFragment();
        detailedInfoFragment = new DetailedInfoFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_repositories_fl_fragment_repo, repositoriesFragment);
        fragmentTransaction.add(R.id.activity_repositories_fl_fragment_detailed, detailedInfoFragment);
        fragmentTransaction.commit();
    }

    void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        LinearLayout ll = findViewById(R.id.linearLayout);
        ll.setVisibility(View.INVISIBLE);
    }

    void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        LinearLayout ll = findViewById(R.id.linearLayout);
        ll.setVisibility(View.VISIBLE);
    }


}
