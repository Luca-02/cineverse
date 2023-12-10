package com.example.cineverse.viewmodel.logged.verified_account.section.home.section;

import android.app.Application;

import com.example.cineverse.data.model.content.section.ContentTv;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionContentViewModel;

public abstract class AbstractSectionTvViewModel
        extends AbstractSectionContentViewModel<ContentTv> {

    public AbstractSectionTvViewModel(Application application) {
        super(application);
    }

}