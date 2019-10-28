package com.example.msfirstlist.domain.start_data_interactor;

import com.example.msfirstlist.repository.net.ApiNetwork;
import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class DefStartDataIteractor implements StartDataIteractor{

    private ApiNetwork apiNetwork;

    @Inject
    DefStartDataIteractor() {
    }

    @Override
    public void setNetworkApi(ApiNetwork api) {
        this.apiNetwork = api;
    }

    @Override
    public Single<List<Repo>> getAll() {
        return apiNetwork.getAll();
    }
}
