package com.example.cineverse.repository.content;

import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;

public class SectionContentRepository
        implements ISectionContentRemoteDataSource {

    private final ISectionContentRemoteDataSource remoteDataSource;

    public SectionContentRepository(ISectionContentRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void fetch(int page) {
        remoteDataSource.fetch(page);
    }

}
