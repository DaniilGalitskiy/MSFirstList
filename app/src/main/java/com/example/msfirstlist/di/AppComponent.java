package com.example.msfirstlist.di;

import android.content.Context;

import com.example.msfirstlist.App;
import com.example.msfirstlist.presenter.main.MainActivity;
import com.example.msfirstlist.presenter.main.MainPresenter;
import com.example.msfirstlist.presenter.repo.RepoPresenter;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;


@Singleton
@Component(modules = {AppModule.class, NavigationModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(MainPresenter presenter);

    void inject(App app);

    void inject(RepoPresenter repoPresenter);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);
        AppComponent build();
    }

}