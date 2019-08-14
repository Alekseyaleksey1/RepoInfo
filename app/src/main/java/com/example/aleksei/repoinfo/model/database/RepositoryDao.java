package com.example.aleksei.repoinfo.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import java.util.List;

import io.reactivex.Single;

@Dao
public interface RepositoryDao {

    @Query("SELECT * FROM repositorymodel")
    Single<List<RepositoryModel>> getAll();

    @Insert
    void insert(List<RepositoryModel> repository);
}
