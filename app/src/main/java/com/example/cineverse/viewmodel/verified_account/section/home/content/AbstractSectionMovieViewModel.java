package com.example.cineverse.viewmodel.verified_account.section.home.content;

import android.app.Application;

import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.repository.content.SectionMovieRepository;

/**
 * The {@link AbstractSectionMovieViewModel} class extends {@link AbstractSectionContentTypeViewModel} is
 * an abstract base class for ViewModel classes representing movie content sections in the home screen.
 */
public abstract class AbstractSectionMovieViewModel
        extends AbstractSectionContentTypeViewModel<Movie> {

    /**
     * Constructs an {@link AbstractSectionMovieViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractSectionMovieViewModel(Application application) {
        this(application, 0);
    }

    /**
     * Constructs an {@link AbstractSectionMovieViewModel} object with the given {@link Application} and genre ID.
     *
     * @param application The {@link Application} of the calling component.
     * @param genre       The genre ID associated with the movie content type.
     */
    public AbstractSectionMovieViewModel(Application application, int genre) {
        super(application, genre);
        repository = new SectionMovieRepository(
                application.getApplicationContext(),
                createRemoteDataSourceInstance(),
                this
        );
    }

}
