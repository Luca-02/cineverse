package com.example.cineverse.viewmodel.logged.verified_account.section.home.section;

import android.app.Application;

import com.example.cineverse.data.model.content.poster.PosterMovie;
import com.example.cineverse.repository.content.poster.PosterMovieRepository;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;

public abstract class AbstractSectionMovieViewModel
        extends AbstractSectionViewModel<PosterMovie> {

    public PosterMovieRepository repository;

    public AbstractSectionMovieViewModel(Application application) {
        super(application);
        this.repository = new PosterMovieRepository(
                application.getBaseContext(), this);
    }

}
