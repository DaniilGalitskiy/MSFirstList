package com.example.msfirstlist.repository.db_repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.msfirstlist.repository.db_repository.dao.RepoDao;
import com.example.msfirstlist.repository.net.entity.Repo;

@Database(entities = Repo.class, version = 1, exportSchema = false)
public abstract class DefAppDatabase extends RoomDatabase implements AppDatabase {

    @Override
    public abstract RepoDao getRepoDao();
}
