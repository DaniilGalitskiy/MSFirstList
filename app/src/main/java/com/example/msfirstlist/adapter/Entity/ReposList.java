package com.example.msfirstlist.adapter.Entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReposList {

    private List<Repo> reposes;

    public void filter(String charText){
        ReposList reposList = new ReposList();
        charText = charText.toLowerCase(Locale.getDefault());
        reposes.clear();
        if (charText.length()==0){
            reposes.addAll(reposList.reposes);
        }
        else {
            for (Repo r : reposList.reposes){
                if (r.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    reposes.add(r);
                }
            }
        }
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

    public List<Repo> getReposes() {
        return reposes;
    }


}
