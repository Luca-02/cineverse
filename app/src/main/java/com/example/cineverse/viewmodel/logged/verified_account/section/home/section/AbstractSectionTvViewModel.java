package com.example.cineverse.viewmodel.logged.verified_account.section.home.section;

import android.app.Application;

import com.example.cineverse.data.model.content.tv.PosterTv;
import com.example.cineverse.repository.content.tv.PosterTvRepository;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;

public abstract class AbstractSectionTvViewModel
        extends AbstractSectionViewModel<PosterTv> {

    public PosterTvRepository repository;

    public AbstractSectionTvViewModel(Application application) {
        super(application);
        this.repository = new PosterTvRepository(application.getBaseContext());
    }

}