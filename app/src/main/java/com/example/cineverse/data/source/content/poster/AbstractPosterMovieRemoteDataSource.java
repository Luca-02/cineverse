package com.example.cineverse.data.source.content.poster;

import android.content.Context;

import com.example.cineverse.data.model.content.section.ContentMovie;
import com.example.cineverse.service.api.MovieApiService;
import com.example.cineverse.utils.ServiceLocator;

public abstract class AbstractPosterMovieRemoteDataSource
        extends AbstractPosterContentRemoteDataSource<ContentMovie> {

    protected final MovieApiService movieApiService;

    public AbstractPosterMovieRemoteDataSource(Context context,
                                               PosterContentResponseCallback<ContentMovie> callback) {
        super(context, callback);
        movieApiService = ServiceLocator.getInstance()
                .getRetrofitService()
                .create(MovieApiService.class);
    }

}
