package com.example.msfirstlist.repository.db_repository;

import com.example.msfirstlist.repository.db_repository.dao.ReposDao;

public interface AppDatabase {
    ReposDao getReposDao();
}
