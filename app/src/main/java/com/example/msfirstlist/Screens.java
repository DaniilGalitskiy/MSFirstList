package com.example.msfirstlist;

import android.app.Fragment;

import com.example.msfirstlist.presenter.main.MainFragment;
import com.example.msfirstlist.presenter.repo.RepoFragment;

import ru.terrakok.cicerone.android.pure.AppScreen;

public class Screens {
    public static final class MainScreen extends AppScreen {

        @Override
        public Fragment getFragment() {
            return MainFragment.getNewInstance();
        }
    }

    public static final class RepoScreen extends AppScreen {
        private final String repo;

        public RepoScreen(String repo) {
            this.repo = repo;
        }

        @Override
        public Fragment getFragment() {
            return new RepoFragment().getNewInstance(repo);
        }
    }
}