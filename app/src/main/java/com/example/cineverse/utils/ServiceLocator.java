package com.example.cineverse.utils;

import static com.example.cineverse.utils.constant.Api.TMDB_API_BASE_URL;

import android.content.Context;

import com.example.cineverse.data.storage.user.UserStorage;
import com.example.cineverse.service.api.MovieApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {

    private static volatile ServiceLocator instance = null;

    private ServiceLocator() {}

    public static synchronized ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }

    public UserStorage getUserStorage(Context context) {
        return new UserStorage(context);
    }

    public Retrofit getRetrofitService() {
        return new Retrofit.Builder()
                .baseUrl(TMDB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}