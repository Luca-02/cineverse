package com.example.cineverse.repository.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.SectionContentRemoteResponseCallback;

public class SectionMovieRepository
        extends AbstractSectionContentRepository<Movie> {

    public SectionMovieRepository(Context context,
                                  AbstractSectionContentRemoteDataSource<Movie> remoteDataSource,
                                  SectionContentRemoteResponseCallback<Movie> callback) {
        super(context, remoteDataSource, Movie.class, callback);
    }

}