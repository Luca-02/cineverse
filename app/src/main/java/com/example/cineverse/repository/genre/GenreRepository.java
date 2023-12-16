package com.example.cineverse.repository.genre;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.genre.GenreApiResponse;
import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.GenresRemoteResponseCallback;
import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;

public class GenreRepository
        implements IGenresRemoteDataSource, GenresRemoteResponseCallback {

    private final AbstractGenresRemoteDataSource remoteDataSource;
    private final GenresRemoteResponseCallback callback;

    public GenreRepository(AbstractGenresRemoteDataSource remoteDataSource, GenresRemoteResponseCallback callback) {
        this.remoteDataSource = remoteDataSource;
        this.callback = callback;
        remoteDataSource.setCallback(this);
    }

    @Override
    public void fetch() {
        remoteDataSource.fetch();
    }

    @Override
    public void onRemoteResponse(GenreApiResponse response) {
        callback.onRemoteResponse(response);
    }

    @Override
    public void onFailure(Failure failure) {
        callback.onFailure(failure);
    }

}
