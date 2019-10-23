package com.example.msfirstlist.adapter.Entity;


import java.util.ArrayList;
import java.util.List;

public class ReposList {

    public ReposList reposList;

    private List<Repos> reposes;

    /*public ReposList getReposList() {

        if (reposList == null) {
            reposList = new ReposList();
        }
        return reposList;

    }*/

    public ReposList() {
        reposes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Repos repos = new Repos();
            repos.setId(i);
            repos.setName("Repos#" + i);
            reposes.add(repos);

        }
    }

    public void addData(Repos c) {
        reposes.add(c);
    }

    public List<Repos> getReposes() {
        return reposes;
    }


}
