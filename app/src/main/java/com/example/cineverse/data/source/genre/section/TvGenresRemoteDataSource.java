package com.example.cineverse.data.source.genre.section;

import android.content.Context;

import com.example.cineverse.data.model.genre.GenreResponse;
import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;

import retrofit2.Call;

/**
 * The {@link TvGenresRemoteDataSource} class extends the
 * {@link AbstractGenresRemoteDataSource} and provides common
 * functionality for remote data sources related to TV genres content.
 */
public class TvGenresRemoteDataSource
        extends AbstractGenresRemoteDataSource
        implements IGenresRemoteDataSource {

    public TvGenresRemoteDataSource(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Call<GenreResponse> createApiCall() {
        return genreApiService.getTvGenres(getLanguage(), getBearerAccessTokenAuth());
    }

}
