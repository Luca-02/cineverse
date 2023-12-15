package com.example.cineverse.repository.genre;

import com.example.cineverse.data.model.Failure;
import com.example.cineverse.data.model.genre.GenreApiResponse;
import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.GenresResponseCallback;
import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;

public class GenreRepository
        implements IGenresRemoteDataSource, GenresResponseCallback {

    private final AbstractGenresRemoteDataSource remoteDataSource;
    private final GenresResponseCallback callback;

    public GenreRepository(AbstractGenresRemoteDataSource remoteDataSource, GenresResponseCallback callback) {
        this.remoteDataSource = remoteDataSource;
        this.callback = callback;
        remoteDataSource.setCallback(this);
    }

    @Override
    public void fetch() {
        remoteDataSource.fetch();
    }

    @Override
    public void onResponse(GenreApiResponse response) {
        callback.onResponse(response);
    }

    @Override
    public void onFailure(Failure failure) {
        callback.onFailure(failure);
    }

}
