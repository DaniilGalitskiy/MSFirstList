package com.example.msfirstlist.presenter.repo;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.msfirstlist.App;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class RepoPresenter extends MvpPresenter<RepoView> {

    @Inject
    Router router;

    public RepoPresenter() {
        App.getAppComponent().inject(this);
    }

    public void onBackPressed(){
        router.exit();
    }
}
