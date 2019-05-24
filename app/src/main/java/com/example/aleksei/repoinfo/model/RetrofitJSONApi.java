package com.example.aleksei.repoinfo.model;

import com.example.aleksei.repoinfo.model.pojo.POJOModelDetailed;
import com.example.aleksei.repoinfo.model.pojo.POJOModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitJSONApi {

    @GET("orgs/square/repos?sort=pushed")
    Call<ArrayList<POJOModel>> getData();

    @GET("repos/{fullName}")
    Call<POJOModelDetailed> getDetailedData(@Path("fullName") String fullName);
}
