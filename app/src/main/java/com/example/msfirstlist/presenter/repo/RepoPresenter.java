package com.example.msfirstlist.presenter.repo;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class RepoPresenter extends MvpPresenter<RepoView> {
    private Router router;
    private String repoName;

    public RepoPresenter(Router router, String repoName) {
        this.router = router;
        this.repoName = repoName;
    }

    public void onBackPressed(){
        router.exit();
    }
}
