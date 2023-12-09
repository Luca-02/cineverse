package com.example.cineverse.viewmodel.logged.verified_account.section.home.section;

import android.app.Application;

import com.example.cineverse.R;
import com.example.cineverse.data.model.content.poster.PosterMovie;
import com.example.cineverse.repository.content.poster.PosterContentRepository;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;

public abstract class AbstractSectionMovieViewModel
        extends AbstractSectionViewModel<PosterMovie> {

    protected PosterContentRepository repository;

    public AbstractSectionMovieViewModel(Application application) {
        super(application);
        this.repository = new PosterContentRepository(
                createRemoteDataSourceInstance()
        );
    }

    @Override
    public void fetch() {
        repository.fetch(1);
    }

}
