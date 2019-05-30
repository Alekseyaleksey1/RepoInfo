package com.example.aleksei.repoinfo.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import java.util.List;

@Dao
public interface RepositoryDao {

    @Query("SELECT * FROM repositorymodel")
    List<RepositoryModel> getAll();

    @Insert
    void insert(RepositoryModel repository);
}
