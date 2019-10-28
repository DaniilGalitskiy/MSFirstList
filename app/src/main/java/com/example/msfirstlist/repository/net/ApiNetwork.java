package com.example.msfirstlist.repository.net;

import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

import io.reactivex.Single;

public interface ApiNetwork {

    Single<List<Repo>> getAll();
}
