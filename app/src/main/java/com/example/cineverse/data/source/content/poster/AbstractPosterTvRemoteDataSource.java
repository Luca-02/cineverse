package com.example.cineverse.data.source.content.poster;

import android.content.Context;

import com.example.cineverse.data.model.content.poster.PosterTv;
import com.example.cineverse.service.api.TvApiService;
import com.example.cineverse.utils.ServiceLocator;

public abstract class AbstractPosterTvRemoteDataSource
        extends AbstractPosterContentRemoteDataSource<PosterTv> {

    protected final TvApiService movieApiService;

    public AbstractPosterTvRemoteDataSource(Context context,
                                            PosterContentResponseCallback<PosterTv> callback) {
        super(context, callback);
        movieApiService = ServiceLocator.getInstance()
                .getRetrofitService()
                .create(TvApiService.class);
    }

}
