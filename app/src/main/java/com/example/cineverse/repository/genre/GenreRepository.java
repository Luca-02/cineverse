package com.example.cineverse.repository.genre;

import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;

public class GenreRepository
        implements IGenresRemoteDataSource {

    private final IGenresRemoteDataSource remoteDataSource;

    public GenreRepository(IGenresRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void fetch() {
        remoteDataSource.fetch();
    }

}
