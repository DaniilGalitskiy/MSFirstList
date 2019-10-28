package com.example.msfirstlist.repository.net;

import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class DefApiNetwork implements ApiNetwork{

    private final static long CONNECT_TIMEOUT = 2500;

    private final Api api;

    @Inject
    public DefApiNetwork(){
        final String URL = "https://api.github.com";

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        api = retrofit.create(Api.class);

    }

    @Override
    public Single<List<Repo>> getAll() {
        return api.getAll();
    }

    public interface Api{

        @GET("users/octocat/repos")
        Single<List<Repo>> getAll();
    }
}
