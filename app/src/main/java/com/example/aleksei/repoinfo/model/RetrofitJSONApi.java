package com.example.aleksei.repoinfo.model;

import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitJSONApi {

    @GET("orgs/square/repos?sort=pushed")
    Call<ArrayList<RepositoryModel>> getData();
}
