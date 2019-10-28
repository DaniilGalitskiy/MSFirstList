package com.example.msfirstlist.di;

import android.content.Context;

import androidx.room.Room;

import com.example.msfirstlist.repository.db_repository.DefAppDatabase;
import com.example.msfirstlist.repository.db_repository.dao.RepoDao;
import com.example.msfirstlist.repository.net.ApiNetwork;
import com.example.msfirstlist.repository.net.DefApiNetwork;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.AndroidSupportInjectionModule;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;


@Module(includes = {AndroidSupportInjectionModule.class})
public class AppModule {

    @Provides
    public RepoDao repoDao(Context context) {
        return Room.databaseBuilder(context, DefAppDatabase.class, "repo")
                .fallbackToDestructiveMigration()
                .build()
                .getRepoDao();
    }

    private Cicerone<Router> cicerone;

    public AppModule() {
        cicerone = Cicerone.create();
    }

    @Provides
    @Singleton
    Router provideRouter() {
        return cicerone.getRouter();
    }

    @Provides
    static ApiNetwork DefApiNetwork(){
        return new DefApiNetwork();
    }

    @Provides
    @Singleton
    NavigatorHolder provideNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

}
