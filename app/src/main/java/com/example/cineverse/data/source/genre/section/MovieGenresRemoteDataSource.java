package com.example.cineverse.data.source.genre.section;

import android.content.Context;

import com.example.cineverse.data.model.genre.GenreApiResponse;
import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;

import retrofit2.Call;

public class MovieGenresRemoteDataSource
        extends AbstractGenresRemoteDataSource
        implements IGenresRemoteDataSource {

    public MovieGenresRemoteDataSource(Context context) {
        super(context);
    }

    @Override
    public void fetch() {
        Call<GenreApiResponse> call =
                genreApiService.getMovieGenres(getLanguage(), getBearerAccessTokenAuth());
        handlePosterApiCal(call);
    }

}
