package com.example.msfirstlist.repository.net;

import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {
    @GET("users/octocat/repos")
    Single<List<Repo>> getAll();
}
