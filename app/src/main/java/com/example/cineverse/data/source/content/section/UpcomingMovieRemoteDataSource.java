package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.MovieResponse;
import com.example.cineverse.data.source.content.AbstractSectionMovieRemoteDataSource;

import retrofit2.Call;

/**
 * The {@link UpcomingMovieRemoteDataSource} class is responsible for fetching remote data.
 */
public class UpcomingMovieRemoteDataSource
        extends AbstractSectionMovieRemoteDataSource {

    public UpcomingMovieRemoteDataSource(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fetch(int page) {
        Call<MovieResponse> call =
                movieApiService.getUpcomingMovies(
                        getLanguage(),
                        page,
                        getRegion(),
                        getBearerAccessTokenAuth()
                );
        handlePosterApiCal(call);
    }

}
