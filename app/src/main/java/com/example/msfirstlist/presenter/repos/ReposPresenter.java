package com.example.msfirstlist.presenter.repos;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ReposPresenter extends MvpPresenter<ReposView> {
    private Router router;
    private String reposName;

    public ReposPresenter(Router router, String reposName) {
        this.router = router;
        this.reposName = reposName;
    }

    public void onBackPressed(){
        router.exit();
    }
}
