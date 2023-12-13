package com.example.cineverse.viewmodel.verified_account.section.home.content;

import android.app.Application;

import com.example.cineverse.data.model.content.section.ContentMovie;

public abstract class AbstractSectionMovieViewModel
        extends AbstractSectionContentTypeViewModel<ContentMovie> {

    public AbstractSectionMovieViewModel(Application application) {
        super(application);
    }

    public AbstractSectionMovieViewModel(Application application, int genre) {
        super(application, genre);
    }

}
