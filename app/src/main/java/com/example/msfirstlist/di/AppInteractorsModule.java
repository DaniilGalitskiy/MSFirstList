package com.example.msfirstlist.di;

import com.example.msfirstlist.domain.db_interactor.DataBaseInteractor;
import com.example.msfirstlist.domain.db_interactor.DefDataBaseInteractor;
import com.example.msfirstlist.domain.start_data_interactor.DefStartDataIteractor;
import com.example.msfirstlist.domain.start_data_interactor.StartDataIteractor;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = {AndroidSupportInjectionModule.class})
abstract class AppInteractorsModule {

    @Provides
    @Singleton
    void DataBaseI


    @Binds
    abstract DataBaseInteractor dataBaseInteractor(DefDataBaseInteractor defDataBaseInteractor);

    @Binds
    abstract StartDataIteractor startDataIteractor(DefStartDataIteractor defStartDataIteractor);
    }