package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.MovieResponse;
import com.example.cineverse.data.source.content.AbstractSectionMovieRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;

import retrofit2.Call;

/**
 * The {@link TopRatedMovieRemoteDataSource} class is responsible for fetching remote data.
 */
public class TopRatedMovieRemoteDataSource
        extends AbstractSectionMovieRemoteDataSource
        implements ISectionContentRemoteDataSource {

    public TopRatedMovieRemoteDataSource(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fetch(int page) {
        Call<MovieResponse> call =
                movieApiService.getTopRatedMovies(
                        getLanguage(),
                        page,
                        getRegion(),
                        getBearerAccessTokenAuth()
                );
        handlePosterApiCal(call);
    }

}
