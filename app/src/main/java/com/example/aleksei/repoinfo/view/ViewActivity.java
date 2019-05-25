package com.example.aleksei.repoinfo.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.aleksei.repoinfo.presenter.ChiefPresenter;
import com.example.aleksei.repoinfo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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


//добавить (при смене ориентации экрана)
//сохранение позиции скролла списка
//сохранение выбранноно эл-та списка
//
//
//

public class ViewActivity extends FragmentActivity {

    @BindView(R.id.activity_repositories_pb)
    ProgressBar progressBar;
    @BindView(R.id.activity_view_ll)
    LinearLayout ll;

    public static ChiefPresenter chiefPresenter;
    public RepositoriesFragment repositoriesFragment;
    public DetailedInfoFragment detailedInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        initializeUI();


    }

    private void initializeUI() {
        ButterKnife.bind(this);
        //progressBar = findViewById(R.id.activity_repositories_pb);
        //ll = findViewById(R.id.activity_view_ll);
        showLoading();
        chiefPresenter = new ChiefPresenter();

        setupFragments();
    }

    private void setupFragments() {
        repositoriesFragment = new RepositoriesFragment();
        detailedInfoFragment = new DetailedInfoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_repositories_fl_fragment_repo, repositoriesFragment);
        fragmentTransaction.replace(R.id.activity_repositories_fl_fragment_detailed, detailedInfoFragment);
        fragmentTransaction.commit();
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        ll.setVisibility(View.INVISIBLE);
    }

    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        ll.setVisibility(View.VISIBLE);
    }

    public void showInternetError() {

    }

}
