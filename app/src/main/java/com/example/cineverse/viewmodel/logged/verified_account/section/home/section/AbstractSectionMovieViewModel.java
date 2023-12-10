package com.example.cineverse.viewmodel.logged.verified_account.section.home.section;

import android.app.Application;

import com.example.cineverse.data.model.content.section.ContentMovie;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionContentViewModel;

public abstract class AbstractSectionMovieViewModel
        extends AbstractSectionContentViewModel<ContentMovie> {

    public AbstractSectionMovieViewModel(Application application) {
        super(application);
    }

}
