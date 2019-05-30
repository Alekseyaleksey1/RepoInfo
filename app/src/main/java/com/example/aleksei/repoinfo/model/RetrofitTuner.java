package com.example.aleksei.repoinfo.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTuner {

    static RetrofitTuner retrofitTuner;
    static Retrofit retrofit;
    static final String BASE_URL = "https://api.github.com/";

    private RetrofitTuner() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitTuner getInstance() {

        if (retrofitTuner == null) {
            retrofitTuner = new RetrofitTuner();
        }
        return retrofitTuner;
    }

    public RetrofitJSONApi getJSONApi() {
        return retrofit.create(RetrofitJSONApi.class);
    }
}
