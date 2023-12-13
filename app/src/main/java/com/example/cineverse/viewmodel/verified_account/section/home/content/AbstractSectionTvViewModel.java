package com.example.cineverse.viewmodel.verified_account.section.home.content;

import android.app.Application;

import com.example.cineverse.data.model.content.section.ContentTv;

public abstract class AbstractSectionTvViewModel
        extends AbstractSectionContentTypeViewModel<ContentTv> {

    public AbstractSectionTvViewModel(Application application) {
        super(application);
    }

    public AbstractSectionTvViewModel(Application application, int genreId) {
        super(application, genreId);
    }

}