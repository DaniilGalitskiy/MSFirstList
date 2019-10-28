package com.example.msfirstlist.repository.db_repository.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.msfirstlist.repository.db_repository.db_entities.DBRepo;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface RepoDao {
    @Query("SELECT * FROM DBRepo")
    Single<List<DBRepo>> getAll();

    @Query("SELECT * FROM DBRepo WHERE id = :id")
    Single<DBRepo> getRepoById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<DBRepo> reposes);

    @Insert
    void insert(DBRepo repos);

    @Update
    void update(DBRepo repos);

    @Delete
    void delete(DBRepo repos);
}
