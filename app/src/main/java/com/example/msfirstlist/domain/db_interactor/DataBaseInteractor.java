package com.example.msfirstlist.domain.db_interactor;

import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DataBaseInteractor {

    Observable<List<Repo>> getAll(String query);

    Completable reloadRepos();
}
