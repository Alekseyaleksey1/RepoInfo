package com.example.aleksei.repoinfo.model;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;


@Database(entities = {RepositoryModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RepositoryDao getRepositoryDao();
}
