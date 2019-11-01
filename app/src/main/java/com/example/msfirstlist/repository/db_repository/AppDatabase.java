package com.example.msfirstlist.repository.db_repository;

import com.example.msfirstlist.repository.db_repository.dao.RepoDao;

public interface AppDatabase {
    RepoDao getRepoDao();
}
