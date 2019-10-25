package com.example.msfirstlist.adapter.Entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReposList {

    public ReposList reposList;

    private List<Repo> reposes;

    /*public ReposList getReposList() {

        if (reposList == null) {
            reposList = new ReposList();
        }
        return reposList;

    }*/


    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        reposList.clear();
        if (charText.length()==0){
            reposList.addAll(reposArrayList);
        }
        else {
            for (Repo r : reposArrayList){
                if (r.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    reposList.add(r);
                }
            }
        }
        notifyDataSetChanged();
    }

    public ReposList() {
        reposes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Repo repo = new Repo();
            repo.setId(i);
            repo.setName("Repo#" + i);
            reposes.add(repo);

        }
    }

    public void addData(Repo c) {
        reposes.add(c);
    }

    public List<Repo> getReposes() {
        return reposes;
    }


}
