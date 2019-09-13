package com.example.aleksei.repoinfo.model.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitManager{
    //private lateinit var retrofitManager : RetrofitManager
    private var retrofit : Retrofit
    private const val BASE_URL : String = "https://api.github.com/"

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

    /*companion object{
        private lateinit var retrofitManager : RetrofitManager
        private lateinit var retrofit : Retrofit
        private val BASE_URL : String = "https://api.github.com/"
    }*/

    /*fun getInstance() : RetrofitManager {

        if(::retrofitManager.isInitialized)
        if(retrofitManager==null){
            retrofitManager = RetrofitManager();
        }
        return retrofitManager
    }*/

    fun getJSONApi() : GitHubApi{
        return retrofit.create(GitHubApi::class.java)
    }
}