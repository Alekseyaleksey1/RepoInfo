package com.example.aleksei.repoinfo.view;

import android.view.View;

public interface RepoListInterface {

    void initializeUI();

    void showLoading();

    void hideLoading();

    void showError(String errorCode);

    void showItemOnClickedPosition(View view);
}
