package com.example.cineverse.repository.genre;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.genre.GenreApiResponse;
import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.GenresRemoteResponseCallback;
import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;

/**
 * The {@link GenreRepository} class acts as an intermediary between the application's data layer
 * and the UI, providing a clean API for data access related to movie genres. It implements the
 * {@link IGenresRemoteDataSource} interface to handle the retrieval of genre data from remote sources.
 */
public class GenreRepository
        implements IGenresRemoteDataSource, GenresRemoteResponseCallback {

    private final AbstractGenresRemoteDataSource remoteDataSource;
    private final GenresRemoteResponseCallback callback;

    public GenreRepository(AbstractGenresRemoteDataSource remoteDataSource, GenresRemoteResponseCallback callback) {
        this.remoteDataSource = remoteDataSource;
        this.callback = callback;
        remoteDataSource.setRemoteResponseCallback(this);
    }

    /**
     * Initiates a request to fetch genre data from the remote data source.
     */
    @Override
    public void fetch() {
        remoteDataSource.fetch();
    }

    /**
     * Callback method invoked when a remote response is received successfully.
     *
     * @param response Response containing genre data.
     */
    @Override
    public void onRemoteResponse(GenreApiResponse response) {
        callback.onRemoteResponse(response);
    }

    /**
     * Callback method invoked when a remote request encounters a failure.
     *
     * @param failure Failure information.
     */
    @Override
    public void onFailure(Failure failure) {
        callback.onFailure(failure);
    }

}
