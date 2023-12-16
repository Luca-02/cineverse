package com.example.cineverse.data.source.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.content.section.MovieResponse;
import com.example.cineverse.service.api.MovieApiService;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

/**
 * The {@link AbstractSectionMovieRemoteDataSource} class extends the
 * {@link AbstractSectionContentRemoteDataSource} and provides common
 * functionality for remote data sources related to movie section content.
 */
public abstract class AbstractSectionMovieRemoteDataSource
        extends AbstractSectionContentRemoteDataSource<Movie> {

    protected final MovieApiService movieApiService;

    public AbstractSectionMovieRemoteDataSource(Context context) {
        super(context);
        movieApiService = ServiceLocator.getInstance().getMovieApiService();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method initiates the process of fetching movies data from the remote data source.
     * It creates and executes the Retrofit API call to get the movies data.
     * </p>
     *
     * @param page The page number of the data to fetch (if applicable).
     * @see #createApiCall(int) The method responsible for creating the Retrofit API call.
     */
    @Override
    public void fetch(int page) {
        Call<MovieResponse> call = createApiCall(page);
        handlePosterApiCall(call);
    }

    /**
     * Creates a Retrofit API call for fetching movies data from the remote data source.
     *
     * @return A Retrofit {@link Call} representing the API call for fetching {@link MovieResponse}.
     */
    protected abstract Call<MovieResponse> createApiCall(int page);

}
