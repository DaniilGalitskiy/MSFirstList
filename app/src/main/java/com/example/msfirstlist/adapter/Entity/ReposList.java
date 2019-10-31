package com.example.msfirstlist.adapter.Entity;


import com.example.msfirstlist.repository.net.entity.Repo;

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

//    public ReposList() {
//        reposes = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            Repos repos = new Repos();
//            repos.setId(i);
//            repos.setName("Repos#" + i);
//            reposes.add(repos);
//
//        }
//    }

//    public ReposList addAll(List<Repo> repoList){
//        ReposList reposList = new ReposList();
//        reposes = new ArrayList<>();
//        for(Repo r : repoList){
//            reposes.add(r);
//        }
//        return reposList;
//    }

    public List<Repo> getReposes() {
        return reposes;
    }


}
