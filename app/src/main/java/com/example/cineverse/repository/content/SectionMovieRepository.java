package com.example.cineverse.repository.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.SectionContentRemoteResponseCallback;

/**
 * The {@link SectionMovieRepository} class extends the {@link AbstractSectionContentRepository}
 * and serves as the repository for managing movie-related section-specific content.
 */
public class SectionMovieRepository
        extends AbstractSectionContentRepository<Movie> {

    /**
     * Constructor for {@link SectionMovieRepository}.
     *
     * @param context         The application context.
     * @param remoteDataSource The remote data source for movie-related section-specific content.
     * @param callback        The callback for handling remote responses.
     */
    public SectionMovieRepository(Context context,
                                  AbstractSectionContentRemoteDataSource<Movie> remoteDataSource,
                                  SectionContentRemoteResponseCallback<Movie> callback) {
        super(context, remoteDataSource, Movie.class, callback);
    }

}