package com.example.msfirstlist.domain.db_interactor;

import com.example.msfirstlist.repository.db_repository.dao.RepoDao;
import com.example.msfirstlist.repository.net.Api;
import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class DefDataBaseInteractor implements DataBaseInteractor {

    private final RepoDao repoDao;
    private Api api;

    public DefDataBaseInteractor(RepoDao repoDao, Api api) {
        this.repoDao = repoDao;
        this.api = api;
    }

    @Override
    public Observable<List<Repo>> getAll(String query) {
        return query.isEmpty() ? repoDao.getAll() : repoDao.getRepoByName(query) ;
    }

    @Override
    public Completable reloadRepos() {
        return api.getAll().doOnSuccess(repoDao::insertAll).ignoreElement();
    }
}
