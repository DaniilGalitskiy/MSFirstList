package com.example.msfirstlist.di;

import android.content.Context;

import androidx.room.Room;

import com.example.msfirstlist.repository.db_repository.DefAppDatabase;
import com.example.msfirstlist.repository.db_repository.dao.ReposDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class AppModule {

    @Singleton
    @Provides
    public DefAppDatabase defAppDatabase(Context context) {
        return Room.databaseBuilder(context, DefAppDatabase.class, "repo")
                .build();
    }

    @Singleton
    @Provides
    ReposDao reposDao(DefAppDatabase defAppDatabase){
        return defAppDatabase.getReposDao();
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
    @Singleton
    NavigatorHolder provideNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }
}
