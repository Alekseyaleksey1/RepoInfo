package com.example.aleksei.repoinfo.model.api;

import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import java.util.ArrayList;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GitHubApi {
    @GET("orgs/square/repos?sort=pushed")
    Observable<ArrayList<RepositoryModel>> getData();
}
