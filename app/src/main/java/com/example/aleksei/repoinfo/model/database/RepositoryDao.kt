package com.example.aleksei.repoinfo.model.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel
import io.reactivex.Single

@Dao
interface RepositoryDao{

    @Query("SELECT * FROM repositorymodel")
    fun getAll() : Single<List<RepositoryModel>>

    @Insert
    fun insert(repository : List<RepositoryModel>)
}