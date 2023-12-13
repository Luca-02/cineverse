package com.example.cineverse.data.source.genre;

import android.content.Context;

import com.example.cineverse.data.model.genre.GenreApiResponse;
import com.example.cineverse.data.source.api.TMDBRemoteDataSource;
import com.example.cineverse.service.api.GenreApiService;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

public abstract class AbstractGenresRemoteDataSource
        extends TMDBRemoteDataSource {

    protected final GenreApiService genreApiService;
    private final GenresResponseCallback callback;

    public AbstractGenresRemoteDataSource(Context context, GenresResponseCallback callback) {
        super(context);
        genreApiService = ServiceLocator.getInstance().getGenreApiService();
        this.callback = callback;
    }

    protected void handlePosterApiCal(Call<GenreApiResponse> call) {
        handlePosterApiCal(call, callback);
    }

}
