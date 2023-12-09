package com.example.cineverse.data.source.content.poster;

import android.content.Context;

import com.example.cineverse.data.model.content.poster.PosterMovie;
import com.example.cineverse.service.api.MovieApiService;
import com.example.cineverse.utils.ServiceLocator;

public abstract class AbstractPosterMovieRemoteDataSource
        extends AbstractPosterContentRemoteDataSource<PosterMovie> {

    protected final MovieApiService movieApiService;

    public AbstractPosterMovieRemoteDataSource(Context context,
                                               PosterContentResponseCallback<PosterMovie> callback) {
        super(context, callback);
        movieApiService = ServiceLocator.getInstance()
                .getRetrofitService()
                .create(MovieApiService.class);
    }

}
