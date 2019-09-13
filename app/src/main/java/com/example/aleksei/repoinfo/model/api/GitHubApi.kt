package com.example.aleksei.repoinfo.model.api

import com.example.aleksei.repoinfo.model.pojo.RepositoryModel
import io.reactivex.Single
import retrofit2.http.GET

interface GitHubApi{
    @GET("orgs/square/repos?sort=pushed")
    fun getData() : Single<List<RepositoryModel>>
}