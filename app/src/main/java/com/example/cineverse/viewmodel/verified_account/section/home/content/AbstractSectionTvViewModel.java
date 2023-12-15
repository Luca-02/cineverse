package com.example.cineverse.viewmodel.verified_account.section.home.content;

import android.app.Application;

import com.example.cineverse.data.model.content.section.TvEntity;
import com.example.cineverse.repository.content.SectionTvRepository;

public abstract class AbstractSectionTvViewModel
        extends AbstractSectionContentTypeViewModel<TvEntity> {

    public AbstractSectionTvViewModel(Application application) {
        this(application, 0);
    }

    public AbstractSectionTvViewModel(Application application, int genreId) {
        super(application, genreId);
        repository = new SectionTvRepository(
                application.getApplicationContext(),
                createRemoteDataSourceInstance(),
                this
        );
    }

}