package com.example.msfirstlist;

import android.app.Application;

import com.example.msfirstlist.di.AppComponent;
import com.example.msfirstlist.di.DaggerAppComponent;

public class App extends Application {
    public static App INSTANCE;
    private static AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        appComponent = DaggerAppComponent
                .builder()
                .build();
        appComponent.inject(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}