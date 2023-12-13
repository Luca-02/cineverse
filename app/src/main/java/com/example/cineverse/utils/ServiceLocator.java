package com.example.cineverse.utils;

import static com.example.cineverse.utils.constant.Api.TMDB_API_BASE_URL;

import com.example.cineverse.service.api.GenreApiService;
import com.example.cineverse.service.api.MovieApiService;
import com.example.cineverse.service.api.TvApiService;

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

    private Retrofit getRetrofitService() {
        return new Retrofit.Builder()
                .baseUrl(TMDB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public MovieApiService getMovieApiService() {
        return getRetrofitService().create(MovieApiService.class);
    }

    public TvApiService getTvApiService() {
        return getRetrofitService().create(TvApiService.class);
    }

    public GenreApiService getGenreApiService() {
        return getRetrofitService().create(GenreApiService.class);
    }

}