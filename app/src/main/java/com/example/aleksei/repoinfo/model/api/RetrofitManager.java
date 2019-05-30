package com.example.aleksei.repoinfo.model.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static RetrofitManager retrofitManager;
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.github.com/";

    private RetrofitManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitManager getInstance() {

        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
        }
        return retrofitManager;
    }

    public GitHubApi getJSONApi() {
        return retrofit.create(GitHubApi.class);
    }
}
