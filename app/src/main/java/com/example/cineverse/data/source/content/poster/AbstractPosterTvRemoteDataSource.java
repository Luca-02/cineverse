package com.example.cineverse.data.source.content.poster;

import android.content.Context;

import com.example.cineverse.data.model.content.section.ContentTv;
import com.example.cineverse.service.api.TvApiService;
import com.example.cineverse.utils.ServiceLocator;

public abstract class AbstractPosterTvRemoteDataSource
        extends AbstractPosterContentRemoteDataSource<ContentTv> {

    protected final TvApiService movieApiService;

    public AbstractPosterTvRemoteDataSource(Context context,
                                            PosterContentResponseCallback<ContentTv> callback) {
        super(context, callback);
        movieApiService = ServiceLocator.getInstance()
                .getRetrofitService()
                .create(TvApiService.class);
    }

}
