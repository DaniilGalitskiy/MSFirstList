package com.example.msfirstlist.domain.start_data_interactor;

import com.example.msfirstlist.repository.net.ApiNetwork;
import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

import io.reactivex.Single;

public interface StartDataIteractor {

    void setNetworkApi(ApiNetwork api);

    Single<List<Repo>> getAll();
}
