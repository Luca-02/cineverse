package com.example.cineverse.data.source.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.ContentMovie;
import com.example.cineverse.service.api.MovieApiService;
import com.example.cineverse.utils.ServiceLocator;

public abstract class AbstractSectionMovieRemoteDataSource
        extends AbstractSectionContentRemoteDataSource<ContentMovie> {

    protected final MovieApiService movieApiService;

    public AbstractSectionMovieRemoteDataSource(Context context,
                                                SectionContentResponseCallback<ContentMovie> callback) {
        super(context, callback);
        movieApiService = ServiceLocator.getInstance().getMovieApiService();
    }

}
