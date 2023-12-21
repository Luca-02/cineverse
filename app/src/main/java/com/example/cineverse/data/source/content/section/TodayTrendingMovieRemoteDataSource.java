package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.MovieResponse;
import com.example.cineverse.data.source.content.AbstractSectionMovieRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;

import retrofit2.Call;

/**
 * The {@link TodayTrendingMovieRemoteDataSource} class is responsible for fetching remote data.
 */
public class TodayTrendingMovieRemoteDataSource
        extends AbstractSectionMovieRemoteDataSource
        implements ISectionContentRemoteDataSource {

    public TodayTrendingMovieRemoteDataSource(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Call<MovieResponse> createApiCall(int page) {
        return movieApiService.getTodayTrendingMovies(
                getLanguage(),
                page,
                getBearerAccessTokenAuth()
        );
    }

}
