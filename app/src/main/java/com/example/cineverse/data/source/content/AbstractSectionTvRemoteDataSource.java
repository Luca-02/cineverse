package com.example.cineverse.data.source.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.ContentTv;
import com.example.cineverse.service.api.TvApiService;
import com.example.cineverse.utils.ServiceLocator;

public abstract class AbstractSectionTvRemoteDataSource
        extends AbstractSectionContentRemoteDataSource<ContentTv> {

    protected final TvApiService movieApiService;

    public AbstractSectionTvRemoteDataSource(Context context,
                                             SectionContentResponseCallback<ContentTv> callback) {
        super(context, callback);
        movieApiService = ServiceLocator.getInstance().getTvApiService();
    }

}
