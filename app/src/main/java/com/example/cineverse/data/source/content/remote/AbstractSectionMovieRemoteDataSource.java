package com.example.cineverse.data.source.content.remote;

import android.content.Context;

import com.example.cineverse.data.model.content.section.MovieEntity;
import com.example.cineverse.service.api.MovieApiService;
import com.example.cineverse.utils.ServiceLocator;

public abstract class AbstractSectionMovieRemoteDataSource
        extends AbstractSectionContentRemoteDataSource<MovieEntity> {

    protected final MovieApiService movieApiService;

    public AbstractSectionMovieRemoteDataSource(Context context) {
        super(context);
        movieApiService = ServiceLocator.getInstance().getMovieApiService();
    }

    @Override
    public String getSection() {
        return SectionContentMapper.getSection(getClass());
    }

}
