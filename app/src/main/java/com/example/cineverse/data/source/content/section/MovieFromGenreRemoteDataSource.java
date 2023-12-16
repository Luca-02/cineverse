package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.MovieResponse;
import com.example.cineverse.data.source.content.AbstractSectionMovieRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;

import retrofit2.Call;

/**
 * The {@link MovieFromGenreRemoteDataSource} class is responsible for fetching remote data
 * from a specific genre.
 */
public class MovieFromGenreRemoteDataSource
        extends AbstractSectionMovieRemoteDataSource
        implements ISectionContentRemoteDataSource {

    private final int genreId;

    public MovieFromGenreRemoteDataSource(Context context, int genreId) {
        super(context);
        this.genreId = genreId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fetch(int page) {
        Call<MovieResponse> call =
                movieApiService.getMovieFromGenres(
                        getLanguage(),
                        page,
                        genreId,
                        getBearerAccessTokenAuth()
                );;
        handlePosterApiCal(call);
    }

}
