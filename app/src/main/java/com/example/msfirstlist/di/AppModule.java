package com.example.msfirstlist.di;

import android.content.Context;

import androidx.room.Room;

import com.example.msfirstlist.domain.db_interactor.DataBaseInteractor;
import com.example.msfirstlist.domain.db_interactor.DefDataBaseInteractor;
import com.example.msfirstlist.repository.db_repository.DefAppDatabase;
import com.example.msfirstlist.repository.db_repository.dao.RepoDao;
import com.example.msfirstlist.repository.net.Api;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.AndroidSupportInjectionModule;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;


@Module(includes = {AndroidSupportInjectionModule.class})
public class AppModule {

    private final static long CONNECT_TIMEOUT = 2500;

    public AppModule() {
        cicerone = Cicerone.create();
    }


    // Data
    @Provides
    public RepoDao repoDao(Context context) {
        return Room.databaseBuilder(context, DefAppDatabase.class, "repo")
                .fallbackToDestructiveMigration()
                .build()
                .getRepoDao();
    }

    @Provides
    static Api api() {
        final String URL = "https://api.github.com";

        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(Api.class);
    }


    // Domain
    @Provides
    @Singleton
    DataBaseInteractor defDataBaseInteractor(RepoDao repoDao, Api api) {
        return new DefDataBaseInteractor(repoDao, api);
    }

    //cicerone
    private Cicerone<Router> cicerone;


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
