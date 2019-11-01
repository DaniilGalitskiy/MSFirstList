package com.example.msfirstlist.presenter.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setSearchQueryText(String query);
    void showError(String msg);
    void setSearchActionViewVisible(boolean isVisible);
    void setVisibleEmptySearchRepos();
    void showLoader(boolean visible);
    void setRepos(List<Repo> repoList);
}
