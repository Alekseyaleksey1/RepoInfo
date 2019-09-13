package com.example.aleksei.repoinfo.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel

@Database(entities = arrayOf(RepositoryModel::class), version = 1)
abstract class ApplicationDatabase : RoomDatabase(){
    abstract fun getRepositoryDao() : RepositoryDao
}