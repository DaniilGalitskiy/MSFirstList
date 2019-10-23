package com.example.msfirstlist.di;

import com.example.msfirstlist.App;
import com.example.msfirstlist.presenter.main.MainActivity;
import com.example.msfirstlist.presenter.main.MainFragment;
import com.example.msfirstlist.presenter.main.MainPresenter;
import com.example.msfirstlist.presenter.repos.ReposFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(App app);

    void inject(MainActivity activity);

    void inject(MainFragment fragment);

    void inject(ReposFragment fragment);

    void inject(MainPresenter presenter);
}