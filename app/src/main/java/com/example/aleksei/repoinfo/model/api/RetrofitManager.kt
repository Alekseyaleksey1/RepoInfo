package com.example.aleksei.repoinfo.model.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager{
    private const val BASE_URL : String = "https://api.github.com/"
    private var retrofit : Retrofit

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

    fun getJSONApi() : GitHubApi{
        return retrofit.create(GitHubApi::class.java)
    }
}