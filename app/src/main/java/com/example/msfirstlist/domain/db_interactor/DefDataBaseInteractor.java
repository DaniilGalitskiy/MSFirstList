package com.example.msfirstlist.domain.db_interactor;

import android.annotation.SuppressLint;

import com.example.msfirstlist.repository.db_repository.dao.RepoDao;
import com.example.msfirstlist.repository.db_repository.db_entities.DBRepo;
import com.example.msfirstlist.repository.net.ApiNetwork;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DefDataBaseInteractor implements DataBaseInteractor {

    private final RepoDao repoDao;
    private ApiNetwork apiNetwork;

    public DefDataBaseInteractor(RepoDao repoDao, ApiNetwork apiNetwork) {
        this.repoDao = repoDao;
        this.apiNetwork = apiNetwork;
    }


    @SuppressLint("CheckResult")
    @Override
    public Single<List<DBRepo>> getAll() {
        return repoDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<DBRepo> getRepoById(long id) {
        return repoDao.getRepoById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public List<Long> insertAll(List<DBRepo> reposes) {
        return repoDao.insertAll(reposes);
    }

    @Override
    public void insertRepo(DBRepo DBRepo) {
        repoDao.insert(DBRepo);
    }

    @Override
    public void updateRepo(DBRepo DBRepo) {
        repoDao.update(DBRepo);
    }

    @Override
    public void deleteRepo(DBRepo DBRepo) {
        repoDao.delete(DBRepo);
    }


    @Override
    public Completable reloadRepos() {
        return apiNetwork.getAll().doOnSuccess(repos -> {
            
            repoDao.insertAll(repos);
        }).ignoreElement();
    }
}
