package com.example.cineverse.data.source.content.remote;

import android.content.Context;

import com.example.cineverse.data.model.content.section.TvEntity;
import com.example.cineverse.service.api.TvApiService;
import com.example.cineverse.utils.ServiceLocator;

public abstract class AbstractSectionTvRemoteDataSource
        extends AbstractSectionContentRemoteDataSource<TvEntity> {

    protected final TvApiService movieApiService;

    public AbstractSectionTvRemoteDataSource(Context context) {
        super(context);
        movieApiService = ServiceLocator.getInstance().getTvApiService();
    }

    @Override
    public String getSection() {
        return SectionContentMapper.getSection(getClass());
    }

}
