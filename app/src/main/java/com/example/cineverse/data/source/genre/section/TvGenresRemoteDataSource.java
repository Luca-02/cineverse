package com.example.cineverse.data.source.genre.section;

import android.content.Context;

import com.example.cineverse.data.model.genre.GenreApiResponse;
import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;

import retrofit2.Call;

public class TvGenresRemoteDataSource
        extends AbstractGenresRemoteDataSource
        implements IGenresRemoteDataSource {

    public TvGenresRemoteDataSource(Context context) {
        super(context);
    }

    @Override
    public void fetch() {
        Call<GenreApiResponse> call =
                genreApiService.getTvGenres(language, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
