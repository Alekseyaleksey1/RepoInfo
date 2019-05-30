package com.example.aleksei.repoinfo.view;

public interface ViewInterface {

    void initializeUI();

    void showLoading();

    void hideLoading();

    void showInternetError(ViewInterface viewInterface);
}
