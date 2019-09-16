package com.example.aleksei.repoinfo.model

import android.arch.persistence.room.Room
import android.content.Context
import com.example.aleksei.repoinfo.model.api.RetrofitManager
import com.example.aleksei.repoinfo.model.database.ApplicationDatabase
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class DataWorker private constructor() {

    private lateinit var db: ApplicationDatabase

    companion object {
        const val INTERNET_DATA_ERROR_CASE: String = "internetDataError"
        const val DB_NAME = "db"
        private var dataWorker: DataWorker? = null

        @Synchronized
        fun getInstance(appContext: Context): DataWorker {
            if (dataWorker == null) {
                dataWorker = DataWorker()
                dataWorker!!.db = Room.databaseBuilder(appContext, ApplicationDatabase::class.java, DB_NAME).build()
            }
            return dataWorker!!
        }
    }

    fun getDataFromInternet(): Single<List<RepositoryModel>> {
        return RetrofitManager.getJSONApi().getData().subscribeOn(Schedulers.io())
    }

    fun saveDataToDatabase(repositoryModelList: List<RepositoryModel>): Completable {
        return Completable
                .create { emitter: CompletableEmitter ->
                    db.getRepositoryDao().insert(repositoryModelList)
                    emitter.onComplete()
                }
                .subscribeOn(Schedulers.io())
    }

    fun getDataFromDatabase(): Single<List<RepositoryModel>> {
        return db.getRepositoryDao().getAll().subscribeOn(Schedulers.io())
    }
}