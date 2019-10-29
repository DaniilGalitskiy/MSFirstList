package com.example.msfirstlist.di;

import android.content.Context;

import com.example.msfirstlist.App;
import com.example.msfirstlist.presenter.main.MainActivity;
import com.example.msfirstlist.presenter.main.MainFragment;
import com.example.msfirstlist.presenter.main.MainPresenter;
import com.example.msfirstlist.presenter.repo.RepoFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;


@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(MainFragment fragment);

    void inject(RepoFragment fragment);

    void inject(MainPresenter presenter);

    void inject(App app);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);
        AppComponent build();
    }

}