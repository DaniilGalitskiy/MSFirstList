package com.example.msfirstlist;

import android.app.Fragment;

import com.example.msfirstlist.presenter.main.MainFragment;
import com.example.msfirstlist.presenter.repos.ReposFragment;

import ru.terrakok.cicerone.android.pure.AppScreen;

public class Screens {
    public static final class MainScreen extends AppScreen {
        private final int number;

        public MainScreen(int number) {
            this.number = number;
            this.screenKey = getClass().getSimpleName() + "_" + number;
        }

        @Override
        public Fragment getFragment() {
            return MainFragment.getNewInstance();
        }
    }

    public static final class ReposScreen extends AppScreen {
        private final String repos;

        public ReposScreen(String repos) {
            this.repos = repos;
        }

        @Override
        public Fragment getFragment() {
            return new ReposFragment().getNewInstance(repos);
        }
    }
}