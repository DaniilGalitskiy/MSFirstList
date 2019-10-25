package com.example.msfirstlist.repository.db_repository.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

@Dao
public interface ReposDao {
    @Query("SELECT * FROM repos")
    List<Repo> getAll();

}
