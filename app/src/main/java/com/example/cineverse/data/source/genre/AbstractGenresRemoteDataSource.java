package com.example.cineverse.data.source.genre;

import android.content.Context;

import com.example.cineverse.data.model.content.section.MovieResponse;
import com.example.cineverse.data.model.genre.GenreApiResponse;
import com.example.cineverse.data.source.api.TMDBRemoteDataSource;
import com.example.cineverse.service.api.GenreApiService;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

/**
 * The {@link AbstractGenresRemoteDataSource} class is an abstract base class for fetching genres
 * remotely. It extends {@link TMDBRemoteDataSource} and implements {@link IGenresRemoteDataSource}.
 */
public abstract class AbstractGenresRemoteDataSource
        extends TMDBRemoteDataSource
        implements IGenresRemoteDataSource {

    protected final GenreApiService genreApiService;
    private GenresRemoteResponseCallback callback;

    public AbstractGenresRemoteDataSource(Context context) {
        super(context);
        genreApiService = ServiceLocator.getInstance().getGenreApiService();
    }

    /**
     * Sets the callback for handling genre-related responses.
     *
     * @param callback The callback to set.
     */
    public void setCallback(GenresRemoteResponseCallback callback) {
        this.callback = callback;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method initiates the process of fetching genres from the remote data source.
     * It creates and executes the Retrofit API call to get the genres data.
     * </p>
     *
     * @see #createApiCall() The method responsible for creating the Retrofit API call.
     */
    @Override
    public void fetch() {
        Call<GenreApiResponse> call = createApiCall();
        handlePosterApiCall(call);
    }

    /**
     * Handles the API call for fetching genres and delegates the response to the callback.
     *
     * @param call The Retrofit call object for fetching genres.
     */
    protected void handlePosterApiCall(Call<GenreApiResponse> call) {
        if (callback != null) {
            handlePosterApiCall(call, callback);
        }
    }

    /**
     * Creates a Retrofit API call for fetching genres data from the remote data source.
     *
     * @return A Retrofit {@link Call} representing the API call for fetching {@link MovieResponse}.
     */
    protected abstract Call<GenreApiResponse> createApiCall();

}
