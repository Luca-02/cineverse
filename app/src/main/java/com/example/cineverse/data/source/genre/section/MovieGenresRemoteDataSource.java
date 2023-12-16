package com.example.cineverse.data.source.genre.section;

import android.content.Context;

import com.example.cineverse.data.model.genre.GenreApiResponse;
import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;

import retrofit2.Call;

/**
 * The {@link MovieGenresRemoteDataSource} class extends the
 * {@link AbstractGenresRemoteDataSource} and provides common
 * functionality for remote data sources related to movie genres content.
 */
public class MovieGenresRemoteDataSource
        extends AbstractGenresRemoteDataSource
        implements IGenresRemoteDataSource {

    public MovieGenresRemoteDataSource(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Call<GenreApiResponse> createApiCall() {
        return genreApiService.getMovieGenres(getLanguage(), getBearerAccessTokenAuth());
    }

}
