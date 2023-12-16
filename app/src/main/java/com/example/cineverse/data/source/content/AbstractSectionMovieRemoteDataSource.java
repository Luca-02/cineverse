package com.example.cineverse.data.source.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.service.api.MovieApiService;
import com.example.cineverse.utils.ServiceLocator;

/**
 * The {@link AbstractSectionMovieRemoteDataSource} class extends the
 * {@link AbstractSectionContentRemoteDataSource} and provides common
 * functionality for remote data sources related to movie section content.
 */
public abstract class AbstractSectionMovieRemoteDataSource
        extends AbstractSectionContentRemoteDataSource<Movie> {

    protected final MovieApiService movieApiService;

    public AbstractSectionMovieRemoteDataSource(Context context) {
        super(context);
        movieApiService = ServiceLocator.getInstance().getMovieApiService();
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
