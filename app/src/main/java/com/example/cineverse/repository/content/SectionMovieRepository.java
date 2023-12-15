package com.example.cineverse.repository.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.MovieEntity;
import com.example.cineverse.data.source.content.remote.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.remote.SectionContentResponseCallback;

public class SectionMovieRepository
        extends AbstractSectionContentRepository<MovieEntity> {

    public SectionMovieRepository(Context context,
                                  AbstractSectionContentRemoteDataSource<MovieEntity> remoteDataSource,
                                  SectionContentResponseCallback<MovieEntity> callback) {
        super(context, remoteDataSource, MovieEntity.class, callback);
    }

}