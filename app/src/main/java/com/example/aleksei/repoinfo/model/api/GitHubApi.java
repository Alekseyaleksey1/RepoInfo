package com.example.aleksei.repoinfo.model.api;

import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import java.util.List;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface GitHubApi {
    @GET("orgs/square/repos?sort=pushed")
    Single<List<RepositoryModel>> getData();
}
