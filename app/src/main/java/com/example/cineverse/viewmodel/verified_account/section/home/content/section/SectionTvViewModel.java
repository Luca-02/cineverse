package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.repository.content.section.SectionTvRepository;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentTypeViewModel;

/**
 * The {@link SectionTvViewModel} class extends {@link AbstractSectionContentTypeViewModel} and
 * is an abstract base class for ViewModel classes representing TV content sections.
 */
public class SectionTvViewModel
        extends AbstractSectionContentTypeViewModel<Tv> {

    /**
     * Constructs a new instance of {@link SectionTvViewModel}.
     *
     * @param application     The application context.
     * @param remoteDataSource The remote data source for TV content sections.
     */
    public SectionTvViewModel(Application application, AbstractSectionContentRemoteDataSource<Tv> remoteDataSource) {
        super(application);
        repository = new SectionTvRepository(
                application.getApplicationContext(),
                remoteDataSource,
                this
        );
    }

}
