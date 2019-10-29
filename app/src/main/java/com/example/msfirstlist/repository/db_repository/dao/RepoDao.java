package com.example.msfirstlist.repository.db_repository.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface RepoDao {
    @Query("SELECT * FROM Repo")
    Observable<List<Repo>> getAll();

    @Query("SELECT * FROM Repo WHERE id = :id")
    Single<Repo> getRepoById(long id);

    @Query("SELECT * FROM Repo WHERE name like :name")
    Observable<List<Repo>> getRepoByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Repo> reposes);

    @Insert
    void insert(Repo repos);

    @Update
    void update(Repo repos);

    @Delete
    void delete(Repo repos);
}
