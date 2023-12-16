package com.example.cineverse.data.source.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.service.api.TvApiService;
import com.example.cineverse.utils.ServiceLocator;

/**
 * The {@link AbstractSectionTvRemoteDataSource} class extends the
 * {@link AbstractSectionContentRemoteDataSource} and provides common
 * functionality for remote data sources related to TV section content.
 */
public abstract class AbstractSectionTvRemoteDataSource
        extends AbstractSectionContentRemoteDataSource<Tv> {

    protected final TvApiService movieApiService;

    public AbstractSectionTvRemoteDataSource(Context context) {
        super(context);
        movieApiService = ServiceLocator.getInstance().getTvApiService();
    }

    /**
     * Gets the section associated with the remote data source.
     *
     * @return The section.
     */
    @Override
    public String getSection() {
        return SectionTypeMappingManager.getSection(getClass());
    }

}
