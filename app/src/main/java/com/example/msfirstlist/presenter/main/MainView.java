package com.example.msfirstlist.presenter.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    void setSearchQueryText(String query);
    void showError(String msg);
    void setSearchActionViewVisible(boolean isVisible);
    void showLoader();
}
