package com.example.msfirstlist.repository.db_repository.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.msfirstlist.repository.net.entity.Repos;

import java.util.List;

@Dao
public interface ReposDao {
    @Query("SELECT * FROM repos")
    List<Repos> getAll();

    @Insert
    void insert(Repos repos);

    @Update
    void update(Repos repos);

    @Delete
    void delete(Repos repos);
}
