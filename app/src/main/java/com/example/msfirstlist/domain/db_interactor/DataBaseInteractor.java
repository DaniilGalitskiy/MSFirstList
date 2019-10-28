package com.example.msfirstlist.domain.db_interactor;

import com.example.msfirstlist.repository.db_repository.db_entities.DBRepo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DataBaseInteractor {

    Single<List<DBRepo>> getAll();

    Single<DBRepo> getRepoById(long id);

    List<Long> insertAll(List<DBRepo> reposes);

    void insertRepo(DBRepo DBRepo);

    void updateRepo(DBRepo DBRepo);

    void deleteRepo(DBRepo DBRepo);

    Completable reloadRepos();
}
