package com.example.aleksei.repoinfo.model;

import com.example.aleksei.repoinfo.model.pojo.ModelPOJODetailed;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJOShort;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitJSONApi {

    @GET("orgs/square/repos?sort=pushed")
    Call<ArrayList<ModelPOJOShort>> getData();

   @GET("repos/{fullName}")
    Call<ModelPOJODetailed> getDetailedData(@Path("fullName") String fullName);
}
