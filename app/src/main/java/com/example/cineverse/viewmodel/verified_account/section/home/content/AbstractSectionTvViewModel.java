package com.example.cineverse.viewmodel.verified_account.section.home.content;

import android.app.Application;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.repository.content.SectionTvRepository;

/**
 * The {@link AbstractSectionTvViewModel} class extends {@link AbstractSectionContentTypeViewModel} and
 * is an abstract base class for ViewModel classes representing TV content sections in the home screen.
 */
public abstract class AbstractSectionTvViewModel
        extends AbstractSectionContentTypeViewModel<Tv> {

    /**
     * Constructs an {@link AbstractSectionTvViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractSectionTvViewModel(Application application) {
        this(application, 0);
    }

    /**
     * Constructs an {@link AbstractSectionTvViewModel} object with the given {@link Application} and genre ID.
     *
     * @param application The {@link Application} of the calling component.
     * @param genreId     The genre ID associated with the TV content type.
     */
    public AbstractSectionTvViewModel(Application application, int genreId) {
        super(application, genreId);
        repository = new SectionTvRepository(
                application.getApplicationContext(),
                createRemoteDataSourceInstance(),
                this
        );
    }

}