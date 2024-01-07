package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.repository.content.section.SectionMovieRepository;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentTypeViewModel;

/**
 * The {@link SectionMovieViewModel} class extends {@link AbstractSectionContentTypeViewModel} and
 * is an abstract base class for ViewModel classes representing movie content sections.
 */
public class SectionMovieViewModel
        extends AbstractSectionContentTypeViewModel<Movie> {

    /**
     * Constructs a new instance of {@link SectionMovieViewModel}.
     *
     * @param application     The application context.
     * @param remoteDataSource The remote data source for movie content sections.
     */
    public SectionMovieViewModel(Application application, AbstractSectionContentRemoteDataSource<Movie> remoteDataSource) {
        super(application);
        repository = new SectionMovieRepository(
                application.getApplicationContext(),
                remoteDataSource,
                this
        );
    }

}