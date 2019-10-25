package com.example.msfirstlist.presenter.main;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.msfirstlist.App;
import com.example.msfirstlist.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;


@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String ARG_SEARCHVISIBLE= "searchVisible";
    private static final String ARG_SEARCHQUERY= "searchQuery";

    private String searchQuery = "";
    private boolean searchVisible;

    @Inject
    Router router;

    public MainPresenter() {
        App.getAppComponent().inject(this);
    }

    public void filter(String newText){
        searchQuery = newText;
        getViewState().setSearchQueryText(searchQuery);
    }

    public void searchVisible(boolean searchVisible){
        this.searchVisible = searchVisible;
        getViewState().setSearchActionViewVisible(searchVisible);
    }

    void onItemClicked(String repoName){
        router.navigateTo(new Screens.RepoScreen(repoName));
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            getViewState().setSearchActionViewVisible(savedInstanceState.getBoolean(ARG_SEARCHVISIBLE));
            getViewState().setSearchQueryText(savedInstanceState.getString(ARG_SEARCHQUERY));
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ARG_SEARCHVISIBLE, searchVisible);
        outState.putString(ARG_SEARCHQUERY, searchQuery);
    }

}
