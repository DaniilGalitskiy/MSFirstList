package com.example.msfirstlist.presenter.main;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.msfirstlist.App;
import com.example.msfirstlist.Screens;
import com.example.msfirstlist.domain.db_interactor.DataBaseInteractor;

import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
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

    private final BehaviorSubject<String> queryBehaviorSubject = BehaviorSubject.createDefault("");
    private final BehaviorSubject<Boolean> isSearchVisibleBehaviorSubject = BehaviorSubject.createDefault(false);

    private Disposable isSearchVisibleDisposable = isSearchVisibleBehaviorSubject.subscribe(isVisible -> {
        getViewState().setSearchActionViewVisible(isVisible);
    });

    private final Disposable itemsDosposable = queryBehaviorSubject.distinctUntilChanged()
            .flatMap(dataBaseInteractor::getAll)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(repoList -> {
                getViewState().setAdapter(repoList);
            });

    public MainPresenter() {
        App.getAppComponent().inject(this);
        reloadRepos();
    }

    public void onQueryTextChanged(String newText) {
        queryBehaviorSubject.onNext(newText);
    }

    public void onSearchMenuItemClick() {
        isSearchVisibleBehaviorSubject.onNext(true);
    }

    public void onCloseSearchClick() {
        isSearchVisibleBehaviorSubject.onNext(false);
        getViewState().setSearchQueryText("");
    }

    void onItemClicked(String repoName) {
        router.navigateTo(new Screens.RepoScreen(repoName));
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

        reloadDisposable = dataBaseInteractor.reloadRepos().subscribe(new Action() {
            @Override
            public void run() throws Exception {
            }
        }, throwable -> {
            if (throwable instanceof ConnectException || throwable instanceof UnknownHostException)
                getViewState().showError("Ошибка соединения reloadRepos()\n" + throwable.getMessage());
            else
                getViewState().showError("Неизвестная ошибка reloadRepos()\n" + throwable.getMessage());
        });
    }

//    private void getAllRepos() {
//        if (reloadDisposable != null) reloadDisposable.dispose();
//        reloadDisposable = dataBaseInteractor.getAll(queryBehaviorSubject.getValue())
//                .subscribe(repos -> {
//                    getViewState().setAdapter(repos);
//                }, throwable -> {
//                    if (throwable instanceof NullPointerException)
//                        getViewState().showError("Ошибка бд getAll()\n" + throwable.getMessage());
//                    else
//                        getViewState().showError("Неизвестная ошибка getAll()\n" + throwable.getMessage());
//                });
//    }


    @Override
    public void onDestroy() {
        if (reloadDisposable != null) reloadDisposable.dispose();
        itemsDosposable.dispose();
        isSearchVisibleDisposable.dispose();
        super.onDestroy();
    }
}
