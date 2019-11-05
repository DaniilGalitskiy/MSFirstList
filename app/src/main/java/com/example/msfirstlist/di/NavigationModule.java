package com.example.msfirstlist.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.AndroidSupportInjectionModule;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module(includes = {AndroidSupportInjectionModule.class})
public class NavigationModule {

    //cicerone
    private final Cicerone<Router> cicerone;

    public NavigationModule() {
        cicerone = Cicerone.create();
    }

    @Provides
    @Singleton
    Router provideRouter() {
        return cicerone.getRouter();
    }


    @Provides
    @Singleton
    NavigatorHolder provideNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }
}
