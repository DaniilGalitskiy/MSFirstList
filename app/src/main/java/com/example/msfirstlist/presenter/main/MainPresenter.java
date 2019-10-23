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

    private static final String ARG_EXPANDED = "expanded";

    @Inject
    Router router;

    private String reposName;
    private boolean expanded;
    private Bundle bundle;

    public MainPresenter() {
        App.INSTANCE.getAppComponent().inject(this);
    }

    void getRepos(){
        getViewState().setAdapter();
    }

    void onCardClicked(String reposName){
        router.navigateTo(new Screens.ReposScreen(reposName));
    }

    void dispose(){ }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            getViewState().setSearchActionViewExpaned(savedInstanceState.getBoolean(ARG_EXPANDED));
        }
    }

    public void onSaveInstanceState(Bundle outState, boolean expanded) {
        outState.putBoolean(ARG_EXPANDED, expanded);
    }

}
