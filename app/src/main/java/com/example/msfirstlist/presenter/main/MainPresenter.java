package com.example.msfirstlist.presenter.main;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.msfirstlist.App;
import com.example.msfirstlist.Screens;
import com.example.msfirstlist.domain.db_interactor.DataBaseInteractor;
import com.example.msfirstlist.repository.net.entity.Repo;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import ru.terrakok.cicerone.Router;


@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String ARG_SEARCHVISIBLE = "onSearchMenuItemClick";
    private static final String ARG_SEARCHQUERY = "searchQuery";

    private @Nullable
    Disposable reloadDisposable = null;

    @Inject
    Router router;

    @Inject
    DataBaseInteractor dataBaseInteractor;

    private final BehaviorSubject<String> queryBehaviorSubject = BehaviorSubject.create();
    private final BehaviorSubject<Boolean> isSearchVisibleBehaviorSubject = BehaviorSubject.createDefault(false);

    private Disposable isSearchVisibleDisposable = isSearchVisibleBehaviorSubject.subscribe(isVisible -> {
        getViewState().setSearchActionViewVisible(isVisible);
    });

    private final Disposable itemsDisposable = queryBehaviorSubject.distinctUntilChanged()
            .flatMap(new Function<String, ObservableSource<List<Repo>>>() {
                @Override
                public ObservableSource<List<Repo>> apply(String query) {
                    return dataBaseInteractor.getAll(query);
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(repoList -> {
                getViewState().setRepos(repoList);
                getViewState().setVisibleEmptySearchRepos();
                getViewState().setSearchQueryText(queryBehaviorSubject.getValue());
            });

    public MainPresenter() {
        App.getAppComponent().inject(this);
        queryBehaviorSubject.onNext("");
        reloadRepos();
    }

    public void onQueryTextChanged(String newText) {
        queryBehaviorSubject.onNext(newText);
    }

    public void onSearchMenuItemClick() {
        isSearchVisibleBehaviorSubject.onNext(true);
    }

    public void onCloseSearchClick() {
        onClearSearchClick();
        isSearchVisibleBehaviorSubject.onNext(false);
    }

    public void onClearSearchClick() {
        queryBehaviorSubject.onNext("");
    }

    void onItemClicked(Repo repo) {
        router.navigateTo(new Screens.RepoScreen(repo.getName()));
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isSearchVisibleBehaviorSubject.onNext(savedInstanceState.getBoolean(ARG_SEARCHVISIBLE));
            getViewState().setSearchQueryText(savedInstanceState.getString(ARG_SEARCHQUERY));
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ARG_SEARCHVISIBLE, isSearchVisibleBehaviorSubject.getValue());
        outState.putString(ARG_SEARCHQUERY, queryBehaviorSubject.getValue());
    }

    public void reloadRepos() {
        if (reloadDisposable != null) reloadDisposable.dispose();
        getViewState().showLoader(true);
        reloadDisposable = dataBaseInteractor.reloadRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> getViewState().showLoader(false))
                .subscribe(() -> {
        }, throwable -> {
            if (throwable instanceof ConnectException || throwable instanceof UnknownHostException)
                getViewState().showError("Ошибка соединения reloadRepos()\n" + throwable.getMessage());
            else
                getViewState().showError("Неизвестная ошибка reloadRepos()\n" + throwable.getMessage());
        });
    }

    @Override
    public void onDestroy() {
        if (reloadDisposable != null) reloadDisposable.dispose();
        itemsDisposable.dispose();
        isSearchVisibleDisposable.dispose();
        super.onDestroy();
    }
}
