package com.example.msfirstlist.repository.db_repository.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

import io.reactivex.Observable;
import kotlin.jvm.Synchronized;

@Dao
public interface RepoDao {

    @Synchronized
    @Query("SELECT * FROM Repo")
    Observable<List<Repo>> getAll();

    @Query("SELECT * FROM Repo WHERE name like '%'||:name||'%'")
    Observable<List<Repo>> getRepoByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Repo> reposes);

    @Synchronized
    @Query("DELETE FROM Repo")
    void removeAll();

    @Transaction
    default void clearAndInsertAll(List<Repo> listRepo) {
        removeAll();
        insertAll(listRepo);
    }
}
