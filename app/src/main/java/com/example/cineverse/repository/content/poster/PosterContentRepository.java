package com.example.cineverse.repository.content.poster;

import com.example.cineverse.data.source.content.poster.IPosterContentRemoteDataSource;

public class PosterContentRepository
        implements IPosterContentRemoteDataSource {

    private final IPosterContentRemoteDataSource remoteDataSource;

    public PosterContentRepository(IPosterContentRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void fetch(int page) {
        remoteDataSource.fetch(page);
    }

}
