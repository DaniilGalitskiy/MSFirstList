package com.example.msfirstlist.repository.net;

import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {

    long CONNECT_TIMEOUT = 2500;
    String URL = "https://api.github.com";

    @GET("users/octocat/repos")
    Single<List<Repo>> getAll();
}
